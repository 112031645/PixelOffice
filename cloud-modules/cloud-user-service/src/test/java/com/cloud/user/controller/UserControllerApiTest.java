package com.cloud.user.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * 用户接口测试 - RestAssured
 * <p>
 * 需先启动 cloud-user-service 服务（端口 8081）。
 */
@DisplayName("用户接口测试 - RestAssured")
class UserControllerApiTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
    }

    @Test
    @DisplayName("查询用户 - 成功")
    void getUserById_success() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/user/{id}", 1)
        .then()
                .statusCode(200)
                .body("code", equalTo("200"))
                .body("data.id", equalTo(1))
                .body("data.username", notNullValue());
    }

    @Test
    @DisplayName("查询用户 - 不存在")
    void getUserById_notFound() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/user/{id}", 99999)
        .then()
                .statusCode(200)
                .body("code", equalTo("200"))
                .body("data", nullValue());
    }

    @Test
    @DisplayName("新增用户 - 成功")
    void createUser_success() {
        String json = """
                {
                  "username": "testuser_%d",
                  "password": "123456",
                  "nickname": "测试用户",
                  "balance": 100.00
                }
                """.formatted(System.currentTimeMillis());

        given()
                .contentType(ContentType.JSON)
                .body(json)
        .when()
                .post("/user")
        .then()
                .statusCode(200)
                .body("code", equalTo("200"))
                .body("data", equalTo(true));
    }

    @Test
    @DisplayName("新增用户 - 参数校验失败（用户名为空）")
    void createUser_paramError() {
        String json = """
                {
                  "password": "123456"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
        .when()
                .post("/user")
        .then()
                .statusCode(400)
                .body("code", equalTo("400"));
    }
}
