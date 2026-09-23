package com.cloud.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.api.order.dto.OrderCreateDTO;
import com.cloud.order.entity.Order;

import java.math.BigDecimal;

/**
 * 订单 Service
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单（分布式事务：本地落单 + 远程扣减用户余额）
     */
    Order createOrder(OrderCreateDTO dto);

    /**
     * 根据订单号查询
     */
    Order getByOrderNo(String orderNo);

    /**
     * 分布式事务联调测试：在 @GlobalTransactional 内远程扣减用户余额 + 本地落单，
     * rollback=true 时主动抛异常，验证跨服务（user-service 余额扣减 + 本地订单）一起回滚。
     */
    Order testSeataCrossService(Long userId, BigDecimal amount, boolean rollback);
}
