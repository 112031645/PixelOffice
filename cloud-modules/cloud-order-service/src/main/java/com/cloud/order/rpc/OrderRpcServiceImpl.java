package com.cloud.order.rpc;

import com.cloud.api.order.OrderRpcService;
import com.cloud.api.order.dto.OrderCreateDTO;
import com.cloud.api.order.dto.OrderDTO;
import com.cloud.order.entity.Order;
import com.cloud.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 订单服务 Dubbo Provider 实现
 */
@Slf4j
@DubboService(version = "1.0.0", timeout = 3000)
@RequiredArgsConstructor
public class OrderRpcServiceImpl implements OrderRpcService {

    private final OrderService orderService;

    @Override
    public OrderDTO getOrderByNo(String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
        if (order == null) {
            return null;
        }
        return OrderDTO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .createTime(order.getCreateTime())
                .build();
    }

    @Override
    public OrderDTO createOrder(OrderCreateDTO dto) {
        Order order = orderService.createOrder(dto);
        return OrderDTO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .createTime(order.getCreateTime())
                .build();
    }
}
