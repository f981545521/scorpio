package cn.acyou.framework.commons.redisExpire;

/**
 * @author youfang
 * @version [1.0.0, 2020/4/26]
 **/
public abstract class RedisKeyExpireListener {

    /**
     * 监听过期KEY
     */
    private String keyName;

    /**
     * 接收过期KEY消息
     * @param keyLast 最后的key
     */
    public abstract void onReceiveMessage(String keyLast);

    /**
     * 设置过期KEY
     * @param keyName 过期KEY
     */
    public RedisKeyExpireListener(String keyName){
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }


}
