package com.cloud.order.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 联调测试生产者 —— 配合 {@link OrderMqConsumer} 验证订单服务的收发链路。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMqProducer {

    private final RocketMQTemplate rocketMqTemplate;

    /** 联调测试专用 topic：DemoController 的 MQ 测试端点会往这里发消息 */
    public static final String TOPIC_ORDER_TEST = "order-test-topic";

    /**
     * 发送联调测试消息（任意字符串内容）
     */
    public void sendTestMessage(String content) {
        log.info("发送测试消息: topic={}, content={}", TOPIC_ORDER_TEST, content);
        rocketMqTemplate.convertAndSend(TOPIC_ORDER_TEST, content);
    }
}
