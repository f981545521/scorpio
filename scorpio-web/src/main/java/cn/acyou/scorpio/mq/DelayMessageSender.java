package cn.acyou.scorpio.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static cn.acyou.scorpio.conf.rabbitmq.RabbitMQConfig.EXCHANGE_ORDER_EXPIRE_DELAY;
import static cn.acyou.scorpio.conf.rabbitmq.RabbitMQConfig.ROUTING_KEY_ORDER_EXPIRE_DELAY;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:30]
 **/
@Component
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void sendDelayMsg(String msg, Integer delayTime) {
        Integer millisecond = delayTime * 1000;
        boolean channelTransacted = rabbitTemplate.isChannelTransacted();
        System.out.println("是否是事务运行：" + channelTransacted);


        //由于有spring的事务参与，而发送操作在提交事务时，是不允许除template的事务有其他事务的参与，所以这里不会提交
        //队列中就没有消息，所以在channel.basicGet时命令返回的是basic.get-empty(队列中没有消息时),而有消息时，返回basic.get-ok
        //String result = transactionTemplate.execute(new TransactionCallback<String>() {
        //    @Override
        //    public String doInTransaction(TransactionStatus status) {
        //        rabbitTemplate.convertAndSend(ROUTING_KEY_ORDER_EXPIRE_DELAY, msg);
        //        return (String) rabbitTemplate.receiveAndConvert(ROUTING_KEY_ORDER_EXPIRE_DELAY);
        //    }
        //});
        ////spring事务完成，对其中的操作需要提交，发送与接收操作被认为是一个事务链而提交
        //System.out.println(result);
        ////这里的执行不受spring事务的影响
        //result = (String) rabbitTemplate.receiveAndConvert(ROUTING_KEY_ORDER_EXPIRE_DELAY);
        //System.out.println(result);



        rabbitTemplate.convertAndSend(EXCHANGE_ORDER_EXPIRE_DELAY, ROUTING_KEY_ORDER_EXPIRE_DELAY, msg, a -> {
            a.getMessageProperties().setDelay(millisecond);
            return a;
        });
    }
}