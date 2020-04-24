package cn.acyou.scorpio.controller.mq;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-24 下午 09:32]
 **/
@Slf4j
@RequestMapping("mq")
@RestController
@Api(value = "学生", description = "RabbitMQ测试", tags = "MQ测试")
public class MqController {
    @Autowired
    private DelayMessageSender sender;

    @GetMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }

}
