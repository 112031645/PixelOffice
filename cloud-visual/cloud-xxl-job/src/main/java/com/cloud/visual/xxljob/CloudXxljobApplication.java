package com.cloud.visual.xxljob;

/**
 * XXL-Job 调度中心（Admin）本地启动项 —— JVM 内嵌方式。
 *
 * <p>xxl-job-admin 2.4.0 本身就是一个 Spring Boot Web 应用（实测基于 Spring Boot 2.7.9）。
 * 官方并未把它发布到 Maven Central（Central 只有 xxl-job / xxl-job-core，以及第三方二次打包件），
 * 因此本模块采用与 cloud-nacos / cloud-sentinel 同款的「本地官方 jar + system scope」方式：
 * 由官方源码自行构建出 admin 的 fat jar，再拆分为「应用类 plain jar」与其自带的 55 个依赖 jar，
 * 全部落在本模块 lib/ 下；此处仅做一层薄包装，把 main 参数透传给其真实启动类
 * {@code com.xxl.job.admin.XxlJobAdminApplication}（取自构建产物 MANIFEST 的 Start-Class），
 * 从而在同一 JVM 内直接拉起调度中心。</p>
 *
 * <p>控制台：<a href="http://127.0.0.1:8080/xxl-job-admin">http://127.0.0.1:8080/xxl-job-admin</a>
 * （默认账号 admin / 123456）。</p>
 *
 * <p><b>前置依赖</b>：xxl-job-admin 默认连接 MySQL 库 {@code xxl_job}。本地快速体验请先准备
 * 一个 MySQL 实例，并在本模块的 {@code src/main/resources/application.yml} 中按本机情况覆盖
 * spring.datasource.url / username / password 指向你本地的库。</p>
 */
public class CloudXxljobApplication {

    public static void main(String[] args) throws Exception {
        // 同 JVM 内嵌：直接反射调用 xxl-job-admin 真实启动类，无需外部进程
        Class.forName("com.xxl.job.admin.XxlJobAdminApplication")
                .getMethod("main", String[].class)
                .invoke(null, (Object) args);
    }
}
