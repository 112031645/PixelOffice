package com.cloud.common.constant;

/**
 * 系统常量
 */
public class CommonConstants {

    private CommonConstants() {}

    /** 默认包名 */
    public static final String BASE_PACKAGE = "com.cloud";

    /** 统一请求头 - Trace ID */
    public static final String HEADER_TRACE_ID = "X-Trace-Id";

    /** 统一请求头 - 用户 ID */
    public static final String HEADER_USER_ID = "X-User-Id";

    /** Redis Key 前缀 */
    public static final String REDIS_KEY_PREFIX = "cloud:";

    /** Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";
}
