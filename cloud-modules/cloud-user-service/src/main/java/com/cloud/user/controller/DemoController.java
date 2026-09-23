package com.cloud.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloud.common.result.Result;
import com.cloud.user.entity.User;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.mq.UserMqProducer;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 中间件联调测试接口：集中演示 Sentinel / Seata / RocketMQ 三大中间件。
 * <p>
 * 所有端点统一挂在 {@code /demo} 前缀下，方便在 Knife4j 文档里归类查看。
 * 通过网关访问时路径为 {@code /user/demo/...}（网关已对 user-service 配置 StripPrefix=1）。
 * </p>
 * <p>
 * 注意：所有 {@code @RequestParam} 都显式写了 {@code name}，不依赖编译器 {@code -parameters}
 * 保留参数名（Spring 6.1 已移除字节码参数名发现，隐式名会抛 IllegalArgumentException）。
 * </p>
 */
@Slf4j
@Tag(name = "中间件联调测试 - Sentinel/Seata/RocketMQ")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final UserMqProducer userMqProducer;
    private final UserMapper userMapper;

    // ============================ Sentinel ============================

    @Operation(summary = "Sentinel-普通资源", description = "调用后可在 Sentinel 控制台 8718 的「簇点链路」看到 sentinel-hello 资源")
    @GetMapping("/sentinel/hello")
    @SentinelResource(value = "sentinel-hello", fallback = "helloFallback", blockHandler = "helloBlock")
    public Result<String> sentinelHello() {
        return Result.success("hello-sentinel");
    }

    @Operation(summary = "Sentinel-慢调用", description = "ms 越大越慢，用于在控制台配「降级规则」触发熔断")
    @GetMapping("/sentinel/slow")
    @SentinelResource(value = "sentinel-slow", fallback = "slowFallback", blockHandler = "slowBlock")
    public Result<String> sentinelSlow(@RequestParam(name = "ms", defaultValue = "0") long ms) throws InterruptedException {
        if (ms > 0) {
            Thread.sleep(ms);
        }
        return Result.success("slow-ok-ms=" + ms);
    }

    /** sentinel-hello 的异常兜底（参数需与原方法一致 + 末尾 Throwable） */
    public Result<String> helloFallback(Throwable t) {
        return Result.fail("sentinel-hello 熔断兜底: " + t.getMessage());
    }

    /** sentinel-hello 的限流/降级(block)处理 */
    public Result<String> helloBlock(BlockException e) {
        return Result.fail("sentinel-hello 被限流/降级(block): " + e.getClass().getSimpleName());
    }

    /** sentinel-slow 的异常兜底 */
    public Result<String> slowFallback(long ms, Throwable t) {
        return Result.fail("sentinel-slow 熔断兜底: ms=" + ms + ", " + t.getMessage());
    }

    /** sentinel-slow 的限流/降级(block)处理 */
    public Result<String> slowBlock(long ms, BlockException e) {
        return Result.fail("sentinel-slow 被限流/降级(block): " + e.getClass().getSimpleName());
    }

    // ============================ Seata（本地 AT 模式分支事务） ============================

    @Operation(summary = "Seata-插入用户并可回滚", description = "在 @GlobalTransactional 内插入 sys_user；rollback=true 时主动抛异常，验证 Seata 将本分支已插入的行回滚（需库中有 undo_log 表）")
    @PostMapping("/seata/user")
    @GlobalTransactional(name = "demo-insert-user", rollbackFor = Exception.class)
    public Result<Long> seataInsertUser(@RequestParam(name = "username", defaultValue = "demo") String username,
                                        @RequestParam(name = "rollback", defaultValue = "false") boolean rollback) {
        User user = new User();
        user.setUsername(username + "-" + UUID.randomUUID().toString().substring(0, 6));
        user.setPassword("123456");
        user.setNickname("测试用户");
        user.setStatus(1);
        user.setBalance(new BigDecimal("0"));
        userMapper.insert(user);
        log.info("[Seata] 本地插入用户 id={}", user.getId());
        if (rollback) {
            throw new RuntimeException("主动触发回滚，验证 Seata 将已插入的用户数据回滚");
        }
        return Result.success(user.getId());
    }

    // ============================ RocketMQ ============================

    @Operation(summary = "RocketMQ-发送测试消息", description = "发送一条字符串消息到 user-test-topic，由 UserMqTestConsumer 消费并打印日志")
    @PostMapping("/mq/send")
    public Result<String> mqSend(@RequestParam(name = "content", defaultValue = "hello-rocketmq") String content) {
        userMqProducer.sendTestMessage(content);
        return Result.success("已发送: " + content);
    }
}
