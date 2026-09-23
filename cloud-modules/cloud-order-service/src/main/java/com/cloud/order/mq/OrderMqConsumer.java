package com.cloud.order.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 联调测试消费者 —— 监听 {@link OrderMqProducer#TOPIC_ORDER_TEST}。
 * 配合 DemoController 的 {@code POST /demo/mq/send} 验证「生产-消费」全链路。
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = OrderMqProducer.TOPIC_ORDER_TEST,
        consumerGroup = "${spring.application.name}-test-consumer-group"
)
public class OrderMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String content) {
        log.info("消费测试消息: content={}", content);
    }
}
