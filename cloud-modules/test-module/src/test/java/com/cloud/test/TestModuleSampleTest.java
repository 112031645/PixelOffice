package com.cloud.test;

import com.cloud.common.result.Result;
import com.cloud.common.result.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test-module 基础测试示例
 */
@DisplayName("test-module 基础测试")
class TestModuleSampleTest {

    @Test
    @DisplayName("统一返回体 - 成功")
    void resultSuccess() {
        Result<String> result = Result.success("hello");
        assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        assertEquals("hello", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("统一返回体 - 失败")
    void resultFail() {
        Result<Void> result = Result.fail(ResultCode.PARAM_VALID_ERROR);
        assertEquals(ResultCode.PARAM_VALID_ERROR.getCode(), result.getCode());
        assertNull(result.getData());
    }
}
