package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.api.user.UserRpcService;
import com.cloud.api.user.dto.UserDTO;
import com.cloud.common.exception.BusinessException;
import com.cloud.common.result.ResultCode;
import com.cloud.order.entity.Order;
import com.cloud.order.mapper.OrderMapper;
import com.cloud.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单 Service 实现
 * <p>
 * 关键：使用 @GlobalTransactional 开启 Seata 分布式事务，
 * 确保「本地落单」与「远程扣减用户余额」操作要么全部成功，要么全部回滚。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @DubboReference(version = "1.0.0", timeout = 3000, check = false)
    private UserRpcService userRpcService;

    @Override
    @GlobalTransactional(name = "create-order-tx", rollbackFor = Exception.class)
    public Order createOrder(com.cloud.api.order.dto.OrderCreateDTO dto) {
        log.info("[Seata] 创建订单开始, userId={}, amount={}", dto.getUserId(), dto.getAmount());

        // 1. 远程调用用户服务 - 查询用户
        UserDTO user = userRpcService.getUserById(dto.getUserId());
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        log.info("[Seata] 查询到用户: {}", user.getUsername());

        // 2. 远程调用用户服务 - 扣减余额（参与分布式事务）
        boolean deducted = userRpcService.deductBalance(dto.getUserId(), dto.getAmount());
        if (!deducted) {
            throw new BusinessException("扣减用户余额失败");
        }
        log.info("[Seata] 扣减用户余额成功");

        // 3. 本地落单
        Order order = Order.builder()
                .orderNo(UUID.randomUUID().toString().replace("-", ""))
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .status(0)
                .remark(dto.getRemark())
                .createTime(LocalDateTime.now())
                .build();
        baseMapper.insert(order);
        log.info("[Seata] 订单落库成功, orderNo={}", order.getOrderNo());

        // 测试分布式事务回滚（取消注释即可验证）
        // int i = 1 / 0;

        return order;
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNo, orderNo));
    }

    @Override
    @GlobalTransactional(name = "demo-cross-service-tx", rollbackFor = Exception.class)
    public Order testSeataCrossService(Long userId, BigDecimal amount, boolean rollback) {
        log.info("[Seata] 联调测试开始, userId={}, amount={}, rollback={}", userId, amount, rollback);

        // 1. 远程查询用户（参与分布式事务）
        UserDTO user = userRpcService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        // 2. 远程扣减用户余额（参与分布式事务，由 user-service 的 RM 注册分支）
        boolean deducted = userRpcService.deductBalance(userId, amount);
        if (!deducted) {
            throw new BusinessException("扣减用户余额失败");
        }
        log.info("[Seata] 远程扣减用户[{}]余额成功", userId);

        // 3. 本地落单（本服务的 RM 注册分支）
        Order order = Order.builder()
                .orderNo(UUID.randomUUID().toString().replace("-", ""))
                .userId(userId)
                .amount(amount)
                .status(0)
                .remark("seata-demo")
                .createTime(LocalDateTime.now())
                .build();
        baseMapper.insert(order);
        log.info("[Seata] 订单落库成功, orderNo={}", order.getOrderNo());

        if (rollback) {
            throw new RuntimeException("主动触发回滚，验证 user-service 余额扣减与本地订单一起回滚");
        }
        return order;
    }
}
