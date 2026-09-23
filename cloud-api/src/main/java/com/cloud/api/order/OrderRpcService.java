package com.cloud.api.order;

import com.cloud.api.order.dto.OrderCreateDTO;
import com.cloud.api.order.dto.OrderDTO;

/**
 * 订单服务 Dubbo 接口契约
 */
public interface OrderRpcService {

    /**
     * 根据订单号查询订单
     */
    OrderDTO getOrderByNo(String orderNo);

    /**
     * 创建订单（分布式事务入口）
     */
    OrderDTO createOrder(OrderCreateDTO dto);
}
