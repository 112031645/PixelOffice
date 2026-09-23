package com.cloud.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类
 */
@EnableDubbo
@EnableDiscoveryClient
@MapperScan("com.cloud.user.mapper")
@SpringBootApplication(scanBasePackages = {"com.cloud"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("""
                ================================
                 cloud-user-service 启动成功!
                 Doc:  http://localhost:8081/doc.html
                ================================
                """);
    }
}
