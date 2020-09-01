package cn.acyou.scorpio.controller.demo;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.system.entity.Student;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 *
 * @author youfang
 * @version [1.0.0, 2020-9-1 下午 09:10]
 **/
@Slf4j
@Controller
@RequestMapping("mq")
public class RocketMQController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private String userTopic = "userTopic";

    @GetMapping("/test1")
    @ResponseBody
    public Result<?> test1(String message) {
        log.info("RocketMQ 准备发送：" + message);
        //SendResult sendResult = rocketMQTemplate.syncSend(userTopic, message);
        rocketMQTemplate.asyncSend(userTopic, MessageBuilder.withPayload(message).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                var1.printStackTrace();
                System.out.printf("async onException Throwable=%s %n", var1);
            }
        }, 3000, 3);
        return Result.success();
    }

    public void testExample(){
        //同步发送
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, Student.builder().age(18).name("Kitty").build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);

        sendResult = rocketMQTemplate.syncSend(userTopic, MessageBuilder.withPayload(
                Student.builder().age(18).name("Kitty").build()).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);
        //异步发送
        rocketMQTemplate.asyncSend(userTopic, "content___", new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }
        });


        // Send request in sync mode with timeout and delayLevel parameter parameter and receive a reply of generic type.
        String replyGenericObject = rocketMQTemplate.sendAndReceive(userTopic, "request generic",
                new TypeReference<String>() {
                }.getType(), 30000, 2);
        System.out.printf("send %s and receive %s %n", "request generic", replyGenericObject);
    }
}
