package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.exception.BusinessException;
import com.cloud.common.redis.RedisService;
import com.cloud.common.result.ResultCode;
import com.cloud.user.entity.User;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 用户 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RedisService redisService;

    private static final String USER_CACHE_KEY = "cloud:user:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deductBalance(Long userId, BigDecimal amount) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        if (user.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("用户余额不足");
        }
        user.setBalance(user.getBalance().subtract(amount));
        boolean updated = baseMapper.updateById(user) > 0;
        if (updated) {
            // 清除缓存
            redisService.delete(USER_CACHE_KEY + userId);
            log.info("扣减用户[{}]余额: {}", userId, amount);
        }
        return updated;
    }

    @Override
    public User getUserByIdWithCache(Long userId) {
        String key = USER_CACHE_KEY + userId;
        User cached = redisService.get(key);
        if (cached != null) {
            return cached;
        }
        User user = baseMapper.selectById(userId);
        if (user != null) {
            redisService.set(key, user, 30, TimeUnit.MINUTES);
        }
        return user;
    }
}
