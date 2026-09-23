package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.user.entity.User;

import java.math.BigDecimal;

/**
 * 用户 Service
 */
public interface UserService extends IService<User> {

    /**
     * 扣减用户余额
     */
    boolean deductBalance(Long userId, BigDecimal amount);

    /**
     * 根据ID查询（带缓存）
     */
    User getUserByIdWithCache(Long userId);
}
