package com.cloud.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / OpenAPI3 配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cloud Framework API")
                        .version("1.0.0")
                        .description("Spring Cloud Alibaba 微服务框架接口文档")
                        .contact(new Contact()
                                .name("Cloud Team")
                                .email("cloud@example.com")));
    }
}
