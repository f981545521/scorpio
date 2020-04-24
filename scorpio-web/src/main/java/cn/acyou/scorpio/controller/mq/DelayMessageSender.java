package cn.acyou.scorpio.controller.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.acyou.scorpio.conf.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static cn.acyou.scorpio.conf.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:30]
 **/
@Component
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMsg(String msg, Integer delayTime) {
        Integer millisecond = delayTime * 1000;
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(millisecond);
            return a;
        });
    }
}