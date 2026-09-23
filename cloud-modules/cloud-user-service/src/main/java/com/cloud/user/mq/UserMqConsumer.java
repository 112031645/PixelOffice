package com.cloud.user.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 消费者 - 监听用户注册消息
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = UserMqProducer.TOPIC_USER_REGISTER,
        consumerGroup = "${spring.application.name}-consumer-group"
)
public class UserMqConsumer implements RocketMQListener<Long> {

    @Override
    public void onMessage(Long userId) {
        log.info("消费用户注册消息: userId={}, 开始发送欢迎邮件...", userId);
        // TODO: 发送欢迎邮件等后续处理
    }
}
