package cn.acyou.scorpio.controller.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static cn.acyou.scorpio.conf.DelayedRabbitMQConfig.DELAYED_QUEUE_NAME;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:29]
 **/
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}