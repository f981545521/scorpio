package cn.acyou.scorpio.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-1 下午 09:29]
 **/
@Slf4j
@Service
@RocketMQMessageListener(topic = "userTopic", consumerGroup = "string_consumer")
public class MessageConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(String message) {
        log.info("MessageConsumer 收到消息：" + message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

    }
}
