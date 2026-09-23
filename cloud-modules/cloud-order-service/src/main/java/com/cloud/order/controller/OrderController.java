package com.cloud.order.controller;

import com.cloud.api.order.dto.OrderCreateDTO;
import com.cloud.common.result.Result;
import com.cloud.order.entity.Order;
import com.cloud.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单 REST 接口
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "创建订单（分布式事务）")
    @PostMapping
    public Result<Order> create(@RequestBody @Valid OrderCreateDTO dto) {
        return Result.success(orderService.createOrder(dto));
    }

    @Operation(summary = "根据订单号查询")
    @GetMapping("/{orderNo}")
    public Result<Order> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(orderService.getByOrderNo(orderNo));
    }
}
