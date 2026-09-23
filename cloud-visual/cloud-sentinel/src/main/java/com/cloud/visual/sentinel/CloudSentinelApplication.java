package com.cloud.visual.sentinel;

/**
 * Sentinel Dashboard 本地启动项 —— JVM 内嵌方式。
 *
 * <p>sentinel-dashboard 1.8.6 本身就是一个 Spring Boot Web 应用（实测基于 Spring Boot 2.5.12）。
 * 官方并未把它发布到 Maven Central（那里只有 0.1.0 / 0.1.1），因此本模块采用与 cloud-nacos 同款的
 * 「本地官方 jar + system scope」方式：由 build 阶段把官方 fat jar 拆分为「应用类 plain jar」
 * 与其自带的 45 个依赖 jar，全部落在本模块 lib/ 下；此处仅做一层薄包装，把 main 参数透传给
 * sentinel-dashboard 真实的启动类 {@code com.alibaba.csp.sentinel.dashboard.DashboardApplication}
 * （取自官方 jar MANIFEST 的 Start-Class，注意包名下的类名是 DashboardApplication 而非 SentinelApplication），
 * 从而在同一 JVM 内直接拉起控制台。</p>
 *
 * <p>控制台：<a href="http://127.0.0.1:8718/">http://127.0.0.1:8718/</a>
 * （默认账号 sentinel / sentinel；端口 8718 为避开网关 8080 调整所得，可用
 * -DSENTINEL_DASHBOARD_PORT=xxxx 覆盖）。规则默认内存态，无需外部数据库。</p>
 */
public class CloudSentinelApplication {

    public static void main(String[] args) throws Exception {
        // 同 JVM 内嵌：直接反射调用 sentinel-dashboard 真实启动类，无需外部进程
        // 类名以官方 jar MANIFEST 的 Start-Class 为准：DashboardApplication（不是 SentinelApplication）
        Class.forName("com.alibaba.csp.sentinel.dashboard.DashboardApplication")
                .getMethod("main", String[].class)
                .invoke(null, (Object) args);
    }
}
