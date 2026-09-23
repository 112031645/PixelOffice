package com.cloud.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * cloud-getway —— 统一 API 网关启动类（Spring Cloud Gateway）。
 *
 * <p>端口 8080，作为本地所有微服务与中间件控制台的单一入口（对应 k8s 里 Higress 的 8080 角色）。
 * 因 Higress 数据面基于 Envoy（Go/C++，无法 JVM 内嵌），本模块用 Spring Cloud Gateway 作为
 * 可同 JVM 内嵌、与本项目 SB3 技术栈一致的等价实现，承担起「8080 代理所有结构」的职责。
 * 路由与跨域配置见 src/main/resources/application.yml。</p>
 *
 * <p>控制台 / 健康检查：http://127.0.0.1:8080/actuator</p>
 */
@SpringBootApplication
public class CloudGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGetwayApplication.class, args);
    }
}
