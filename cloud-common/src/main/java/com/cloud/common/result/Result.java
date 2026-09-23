package com.cloud.common.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一返回体
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 返回码 */
    private String code;

    /** 返回消息 */
    private String message;

    /** 数据 */
    private T data;

    /** 时间戳 */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /** 成功 - 有数据 */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /** 成功 - 无数据 */
    public static <T> Result<T> success() {
        return success(null);
    }

    /** 成功 - 自定义消息 */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /** 失败 - 默认 */
    public static <T> Result<T> fail() {
        return fail(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage());
    }

    /** 失败 - 自定义消息 */
    public static <T> Result<T> fail(String message) {
        return fail(ResultCode.FAIL.getCode(), message);
    }

    /** 失败 - 自定义码和消息 */
    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null);
    }

    /** 失败 - 使用结果码 */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
