package com.cloud.api.user;

import com.cloud.api.user.dto.UserDTO;
import jakarta.validation.constraints.NotNull;

/**
 * 用户服务 Dubbo 接口契约
 * <p>
 * 供其他服务（如订单服务）通过 RPC 调用用户服务。
 * 此模块仅定义接口与 DTO，不包含实现。
 */
public interface UserRpcService {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserDTO getUserById(@NotNull(message = "用户ID不能为空") Long userId);

    /**
     * 扣减用户余额
     *
     * @param userId 用户ID
     * @param amount 扣减金额
     * @return 是否扣减成功
     */
    boolean deductBalance(@NotNull(message = "用户ID不能为空") Long userId,
                          @NotNull(message = "金额不能为空") java.math.BigDecimal amount);
}
