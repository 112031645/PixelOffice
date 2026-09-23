package com.alibaba.nacos;

import com.alibaba.nacos.sys.filter.NacosTypeExcludeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Nacos Server 启动类（cloud-nacos 模块）。
 *
 * <p>本类复制自 Nacos 2.3.0 官方 console 模块的启动类
 * ({@code com.alibaba.nacos.Nacos})，仅在 main 中固定单机模式。
 * 运行本类即可启动 Nacos Server，控制台地址
 * <a href="http://127.0.0.1:8848/nacos">http://127.0.0.1:8848/nacos</a>（nacos / nacos）。</p>
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.alibaba.nacos", excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = {NacosTypeExcludeFilter.class}),
        @Filter(type = FilterType.CUSTOM, classes = {TypeExcludeFilter.class}),
        @Filter(type = FilterType.CUSTOM, classes = {AutoConfigurationExcludeFilter.class})})
@ServletComponentScan
@EnableScheduling
public class Nacos {

    public static void main(String[] args) {
        // 单机模式启动（集群模式需搭配 conf/cluster.conf，详见 Nacos 官方文档）
        System.setProperty("nacos.standalone", "true");
        System.setProperty("server.tomcat.accesslog.enabled", "false");
        SpringApplication.run(Nacos.class, args);
    }
}
