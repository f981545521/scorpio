package cn.acyou.scorpio.conf.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:46]
 **/
@Configuration
@EnableTransactionManagement
public class RabbitMQConfig {
    //订单超时队列
    public static final String QUEUE_ORDER_EXPIRE_DELAY =    "delay.order.expire.queue";
    //订单超时交换机
    public static final String EXCHANGE_ORDER_EXPIRE_DELAY = "delay.order.expire.exchange";
    //订单路由名称
    public static final String ROUTING_KEY_ORDER_EXPIRE_DELAY =   "delay.order.expire.routingkey";

    @Bean("QUEUE_ORDER_EXPIRE_DELAY")
    public Queue queueOrderExpireDelay() {
        return new Queue(QUEUE_ORDER_EXPIRE_DELAY);
    }

    /**
     * message 先发送到 delayExchange，delayExchange 接收后，
     * 根据 binding 的 delayQueue 的 ttl 时间，到期后，再通过 x-dead-letter-exchange 设置的 exchange，
     * 把消息发送到 businessExchange，businessExchange 的 businessQueue 接收到消息后就可以实现具体的业务处理。
     */
    @Bean("EXCHANGE_ORDER_EXPIRE_DELAY")
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_ORDER_EXPIRE_DELAY, "x-delayed-message", true, false, args);
    }

    //发送消息的队列绑定到交换机上
    @Bean
    public Binding bindingOrderExpire(@Qualifier("QUEUE_ORDER_EXPIRE_DELAY") Queue queue,
                                 @Qualifier("EXCHANGE_ORDER_EXPIRE_DELAY") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ORDER_EXPIRE_DELAY).noargs();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplateNew(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setChannelTransacted(true);
        return template;
    }
}