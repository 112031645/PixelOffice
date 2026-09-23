package com.cloud.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单服务启动类
 */
@EnableDubbo
@EnableDiscoveryClient
@MapperScan("com.cloud.order.mapper")
@SpringBootApplication(scanBasePackages = {"com.cloud"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("""
                ================================
                 cloud-order-service 启动成功!
                 Doc:  http://localhost:8082/doc.html
                ================================
                """);
    }
}
