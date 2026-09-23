package com.cloud.user.rpc;

import com.cloud.api.user.UserRpcService;
import com.cloud.api.user.dto.UserDTO;
import com.cloud.common.exception.BusinessException;
import com.cloud.common.result.ResultCode;
import com.cloud.user.entity.User;
import com.cloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;

/**
 * 用户服务 Dubbo Provider 实现
 * <p>
 * 对外暴露 RPC 接口，供其他服务调用。
 */
@Slf4j
@DubboService(version = "1.0.0", timeout = 3000)
@RequiredArgsConstructor
public class UserRpcServiceImpl implements UserRpcService {

    private final UserService userService;

    @Override
    public UserDTO getUserById(Long userId) {
        log.info("[Dubbo] 查询用户: userId={}", userId);
        User user = userService.getUserByIdWithCache(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .balance(user.getBalance())
                .status(user.getStatus())
                .createTime(user.getCreateTime())
                .build();
    }

    @Override
    public boolean deductBalance(Long userId, BigDecimal amount) {
        log.info("[Dubbo] 扣减余额: userId={}, amount={}", userId, amount);
        return userService.deductBalance(userId, amount);
    }
}
