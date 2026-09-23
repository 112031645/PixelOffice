package com.cloud.visual.seata;

/**
 * Seata Server 本地启动项 —— JVM 内嵌方式。
 *
 * <p>seata-server 2.0.0 本身就是一个 Spring Boot Web 应用（基于 Spring Boot 2.7.17）。
 * 本模块通过模块级 BOM 将 Spring Boot 版本锁定到 2.7.17，并把 seata-server 作为普通
 * 依赖引入；此处仅做一层薄包装，在透传参数前强制以「file 单机模式」启动，再反射调用
 * seata-server 真实的启动类 {@code io.seata.server.ServerApplication}，从而在同一 JVM 内
 * 直接拉起事务协调器（TC）。</p>
 *
 * <p>事务服务端口：8091；Seata 2.0 内置控制台默认 7091。
 * file 模式下配置与存储均落本地文件，无需 MySQL / 注册中心即可本地联调。</p>
 */
public class CloudSeataApplication {

    public static void main(String[] args) throws Exception {
        // 强制 file 单机模式：配置（seata.config.type）与存储（seata.store.mode）均为本地文件，
        // 使 Seata 可在完全不依赖外部 MySQL / 注册中心的情况下本地启动。
        System.setProperty("seata.config.type", "file");
        System.setProperty("seata.store.mode", "file");
        // 同 JVM 内嵌：直接反射调用 seata-server 真实启动类，无需外部进程
        Class.forName("io.seata.server.ServerApplication")
                .getMethod("main", String[].class)
                .invoke(null, (Object) args);
    }
}
