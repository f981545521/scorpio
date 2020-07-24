package cn.acyou.framework.utils.redis;

import cn.acyou.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/24]
 **/
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_KEY_PREFIX = "REDIS_LOCK:";
    //默认超时时间 60S
    private static final int DEFAULT_TIME_OUT = 60 * 1000;

    /**
     * 获得锁
     *
     * @param lockKey 锁定键
     * @return {@link String}
     */
    public String lock(@NotNull String lockKey) {
        return lock(lockKey, DEFAULT_TIME_OUT);
    }

    /**
     * 获得锁
     *
     * @param lockKey     锁定键
     * @param milliSecond 毫秒
     * @return {@link String}
     */
    public String lock(@NotNull String lockKey, long milliSecond) {
        if (StringUtils.isEmpty(lockKey)){
            throw new ServiceException("lockKey must not be null .");
        }
        String lockId = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY_PREFIX + lockKey, lockId, milliSecond, TimeUnit.MILLISECONDS);
        if (success != null && success){
            return lockId;
        }
        return null;
    }

    /**
     * 解锁
     *
     * @param lockKey 锁Key
     * @param lockId  锁标识
     * @return boolean 是否成功
     */
    public boolean unLock(String lockKey, String lockId) {
        if (StringUtils.isEmpty(lockKey)){
            return false;
        }
        if (StringUtils.isEmpty(lockId)){
            return false;
        }
        String currentLockId = redisTemplate.opsForValue().get(LOCK_KEY_PREFIX + lockKey);
        if (currentLockId != null && currentLockId.equals(lockId)){
            redisTemplate.delete(LOCK_KEY_PREFIX + lockKey);
            return true;
        }
        return false;
    }
}
