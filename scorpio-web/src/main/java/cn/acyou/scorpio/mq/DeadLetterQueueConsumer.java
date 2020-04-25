package cn.acyou.scorpio.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static cn.acyou.scorpio.conf.rabbitmq.RabbitMQConfig.QUEUE_ORDER_EXPIRE_DELAY;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:29]
 **/
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = QUEUE_ORDER_EXPIRE_DELAY)
    public void delayQueueConsumer(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列收到消息：{}", new Date().toString(), msg);
        if (msg.equals("error")) {
            //否认消息，否认后立即重试
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
        //手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        /*
         * deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag， 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
         * multiple：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
         */
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        /*
         * deliveryTag（唯一标识 ID）：上面已经解释了。
         * multiple：上面已经解释了。
         * requeue： true ：重回队列，false ：丢弃，我们在nack方法中必须设置 false，否则重发没有意义。
         */
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

        /*
         * deliveryTag（唯一标识 ID）：上面已经解释了。
         * requeue：上面已经解释了，在reject方法里必须设置true。
         */
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}