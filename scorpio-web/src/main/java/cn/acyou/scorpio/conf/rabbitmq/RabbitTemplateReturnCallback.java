package cn.acyou.scorpio.conf.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 如果消息没有到exchange,则confirm回调,ack=false
 * 如果消息到达exchange,则confirm回调,ack=true
 * exchange到queue成功,则不回调return
 * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
 *
 * RabbitMq ReturnCallback回调
 * @author youfang
 * @version [1.0.0, 2020-4-25 下午 01:15]
 **/
@Component
public class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("returned回调--:message:" + message + ",replyText:" + replyText + ",routingKey:" + routingKey);
    }
}
