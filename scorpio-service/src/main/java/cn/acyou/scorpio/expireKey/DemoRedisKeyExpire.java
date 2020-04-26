package cn.acyou.scorpio.expireKey;

import cn.acyou.framework.commons.redisExpire.RedisExpireKey;
import cn.acyou.framework.commons.redisExpire.RedisKeyExpireListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 团购过期未完成任务
 * @author youfang
 * @version [1.0.0, 2020/4/26]
 **/
@Slf4j
@Component
public class DemoRedisKeyExpire extends RedisKeyExpireListener {


    DemoRedisKeyExpire(){
        super(RedisExpireKey.OK);
    }

    /**
     * 接收过期KEY消息
     *
     * @param keyLast 最后的key
     */
    @Override
    public void onReceiveMessage(String keyLast) {
        log.info("DemoRedisKeyExpire接收过期KEY消息:" + keyLast);


    }
}
