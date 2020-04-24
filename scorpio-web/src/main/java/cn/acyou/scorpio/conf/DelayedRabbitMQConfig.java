package cn.acyou.scorpio.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:46]
 **/
@Configuration
public class DelayedRabbitMQConfig {
    public static final String DELAYED_QUEUE_NAME =    "delay.order.expire.queue";
    public static final String DELAYED_EXCHANGE_NAME = "delay.order.expire.exchange";
    public static final String DELAYED_ROUTING_KEY =   "delay.order.expire.routingkey";

    @Bean
    public Queue immediateQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    /**
     * message 先发送到 delayExchange，delayExchange 接收后，
     * 根据 binding 的 delayQueue 的 ttl 时间，到期后，再通过 x-dead-letter-exchange 设置的 exchange，
     * 把消息发送到 businessExchange，businessExchange 的 businessQueue 接收到消息后就可以实现具体的业务处理。
     * @return
     */
    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingNotify(@Qualifier("immediateQueue") Queue queue,
                                 @Qualifier("customExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}