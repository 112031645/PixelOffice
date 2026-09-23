package com.cloud.user.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 消息生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserMqProducer {

    private final RocketMQTemplate rocketMqTemplate;

    public static final String TOPIC_USER_REGISTER = "user-register-topic";

    /** 联调测试专用 topic：DemoController 的 MQ 测试端点会往这里发消息 */
    public static final String TOPIC_USER_TEST = "user-test-topic";

    /**
     * 发送用户注册消息
     */
    public void sendUserRegisterMessage(Long userId) {
        log.info("发送用户注册消息: userId={}", userId);
        rocketMqTemplate.convertAndSend(TOPIC_USER_REGISTER, userId);
    }

    /**
     * 发送联调测试消息（任意字符串内容），配合 {@link UserMqTestConsumer} 验证收发链路
     */
    public void sendTestMessage(String content) {
        log.info("发送测试消息: topic={}, content={}", TOPIC_USER_TEST, content);
        rocketMqTemplate.convertAndSend(TOPIC_USER_TEST, content);
    }
}
