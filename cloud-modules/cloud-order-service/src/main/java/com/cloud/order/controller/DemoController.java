package com.cloud.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloud.common.result.Result;
import com.cloud.order.entity.Order;
import com.cloud.order.mq.OrderMqProducer;
import com.cloud.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 中间件联调测试接口：集中演示 Sentinel / Seata / RocketMQ 三大中间件。
 * <p>
 * 所有端点统一挂在 {@code /demo} 前缀下，方便在 Knife4j 文档里归类查看。
 * 通过网关访问时路径为 {@code /order/demo/...}（网关已对 order-service 配置 StripPrefix=1）。
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

    private final OrderMqProducer orderMqProducer;
    private final OrderService orderService;

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

    public Result<String> helloFallback(Throwable t) {
        return Result.fail("sentinel-hello 熔断兜底: " + t.getMessage());
    }

    public Result<String> helloBlock(BlockException e) {
        return Result.fail("sentinel-hello 被限流/降级(block): " + e.getClass().getSimpleName());
    }

    public Result<String> slowFallback(long ms, Throwable t) {
        return Result.fail("sentinel-slow 熔断兜底: ms=" + ms + ", " + t.getMessage());
    }

    public Result<String> slowBlock(long ms, BlockException e) {
        return Result.fail("sentinel-slow 被限流/降级(block): " + e.getClass().getSimpleName());
    }

    // ============================ Seata（跨服务分支事务） ============================

    @Operation(summary = "Seata-跨服务下单并可回滚", description = "调用 OrderService.testSeataCrossService（@GlobalTransactional）：远程扣减 user-service 余额 + 本地落单；rollback=true 验证跨服务一起回滚。需 user-service 已注册到 Nacos 且 userId 存在。")
    @PostMapping("/seata/order")
    public Result<Long> seataOrder(@RequestParam(name = "userId") Long userId,
                                   @RequestParam(name = "amount", defaultValue = "10") BigDecimal amount,
                                   @RequestParam(name = "rollback", defaultValue = "false") boolean rollback) {
        Order order = orderService.testSeataCrossService(userId, amount, rollback);
        return Result.success(order.getId());
    }

    // ============================ RocketMQ ============================

    @Operation(summary = "RocketMQ-发送测试消息", description = "发送一条字符串消息到 order-test-topic，由 OrderMqConsumer 消费并打印日志")
    @PostMapping("/mq/send")
    public Result<String> mqSend(@RequestParam(name = "content", defaultValue = "hello-rocketmq") String content) {
        orderMqProducer.sendTestMessage(content);
        return Result.success("已发送: " + content);
    }
}
