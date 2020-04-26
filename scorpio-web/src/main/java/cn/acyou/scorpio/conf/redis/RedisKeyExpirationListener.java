package cn.acyou.scorpio.conf.redis;

import cn.acyou.framework.commons.redisExpire.RedisKeyExpireListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * http://doc.redisfans.com/topic/notification.html
 * <p>
 * 因为 Redis 目前的订阅与发布功能采取的是发送即忘（fire and forget）策略，
 * 所以如果你的程序需要可靠事件通知（reliable notification of events），
 * 那么目前的键空间通知可能并不适合你： 当订阅事件的客户端断线时， 它会丢失所有在断线期间分发给它的事件。
 * 注意：
 * <pre>
 *  需要修改redis.windows.conf配置文件中notify-keyspace-events的值为Ex
 *  notify-keyspace-events Ex
 * </pre>
 *
 * @author youfang
 * @version [1.0.0, 2020/4/26]
 **/
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private List<RedisKeyExpireListener> expireListeners = new ArrayList<>();

    public RedisKeyExpirationListener(RedisMessageListenerContainer container) {
        super(container);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        log.info("Redis 接受消息：{}", key);
        if (key != null) {
            String keyBefore = key.substring(0, key.lastIndexOf(":") + 1);
            String keyLast = key.substring(key.lastIndexOf(":") + 1);
            for (RedisKeyExpireListener expireListener : expireListeners) {
                if (expireListener.getKeyName().equals(keyBefore)) {
                    expireListener.onReceiveMessage(keyLast);
                }
            }
        }
    }
}
