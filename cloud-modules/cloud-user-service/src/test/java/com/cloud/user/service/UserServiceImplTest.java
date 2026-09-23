package com.cloud.user.service;

import com.cloud.common.exception.BusinessException;
import com.cloud.common.redis.RedisService;
import com.cloud.user.entity.User;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * 用户 Service 单元测试 - JUnit5 + Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("用户服务单元测试")
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        // MyBatis-Plus ServiceImpl 的 baseMapper 字段需手动注入
        ReflectionTestUtils.setField(userService, "baseMapper", userMapper);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setBalance(new BigDecimal("1000.00"));
        mockUser.setStatus(1);
    }

    @Test
    @DisplayName("扣减余额成功")
    void deductBalance_success() {
        when(userMapper.selectById(1L)).thenReturn(mockUser);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        boolean result = userService.deductBalance(1L, new BigDecimal("100.00"));

        assertTrue(result);
        assertEquals(new BigDecimal("900.00"), mockUser.getBalance());
        verify(redisService, times(1)).delete("cloud:user:1");
    }

    @Test
    @DisplayName("扣减余额失败 - 用户不存在")
    void deductBalance_userNotFound() {
        when(userMapper.selectById(999L)).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.deductBalance(999L, new BigDecimal("100.00")));
        assertEquals("1001", ex.getCode());
    }

    @Test
    @DisplayName("扣减余额失败 - 余额不足")
    void deductBalance_insufficientBalance() {
        when(userMapper.selectById(1L)).thenReturn(mockUser);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.deductBalance(1L, new BigDecimal("2000.00")));
        assertEquals("用户余额不足", ex.getMessage());
    }

    @Test
    @DisplayName("查询用户 - 缓存命中")
    void getUserByIdWithCache_hit() {
        when(redisService.get("cloud:user:1")).thenReturn(mockUser);

        User user = userService.getUserByIdWithCache(1L);

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        verify(userMapper, never()).selectById(anyLong());
    }

    @Test
    @DisplayName("查询用户 - 缓存未命中，查询数据库")
    void getUserByIdWithCache_miss() {
        when(redisService.get(anyString())).thenReturn(null);
        when(userMapper.selectById(1L)).thenReturn(mockUser);

        User user = userService.getUserByIdWithCache(1L);

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        verify(redisService, times(1)).set(eq("cloud:user:1"), any(), anyLong(), any());
    }
}
