package com.cloud.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /** 成功 */
    SUCCESS("200", "操作成功"),
    /** 失败 */
    FAIL("500", "操作失败"),

    /** 参数校验失败 */
    PARAM_VALID_ERROR("400", "参数校验失败"),
    /** 未授权 */
    UNAUTHORIZED("401", "未授权或令牌失效"),
    /** 禁止访问 */
    FORBIDDEN("403", "禁止访问"),
    /** 资源不存在 */
    NOT_FOUND("404", "资源不存在"),
    /** 请求方法不支持 */
    METHOD_NOT_SUPPORTED("405", "请求方法不支持"),

    /** 系统异常 */
    SYSTEM_ERROR("500", "系统内部异常"),
    /** 服务不可用 */
    SERVICE_UNAVAILABLE("503", "服务不可用"),
    /** 网关错误 */
    GATEWAY_ERROR("504", "网关错误"),

    /** 业务异常 - 用户 */
    USER_NOT_FOUND("1001", "用户不存在"),
    USER_PASSWORD_ERROR("1002", "密码错误"),
    USER_DISABLED("1003", "用户已禁用"),
    USER_ALREADY_EXISTS("1004", "用户已存在"),

    /** 业务异常 - 订单 */
    ORDER_NOT_FOUND("2001", "订单不存在"),
    ORDER_STATUS_ERROR("2002", "订单状态错误"),
    STOCK_NOT_ENOUGH("2003", "库存不足"),

    /** 分布式事务 */
    DISTRIBUTED_TRANSACTION_ERROR("3001", "分布式事务执行失败"),

    /** 限流 */
    FLOW_LIMITED("429", "请求过于频繁，请稍后再试"),
    /** 熔断 */
    DEGRADED("503", "服务已降级，请稍后再试");

    private final String code;
    private final String message;
}
