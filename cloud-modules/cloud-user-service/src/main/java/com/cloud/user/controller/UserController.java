package com.cloud.user.controller;

import com.cloud.common.result.Result;
import com.cloud.user.entity.User;
import com.cloud.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 REST 接口
 */
@Tag(name = "用户管理")
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable @NotNull(message = "用户ID不能为空") Long id) {
        return Result.success(userService.getUserByIdWithCache(id));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public Result<Boolean> save(@RequestBody @Validated User user) {
        return Result.success(userService.save(user));
    }

    @Operation(summary = "修改用户")
    @PutMapping
    public Result<Boolean> update(@RequestBody @Validated User user) {
        return Result.success(userService.updateById(user));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(userService.removeById(id));
    }
}
