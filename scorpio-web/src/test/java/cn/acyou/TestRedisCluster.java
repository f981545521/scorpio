package cn.acyou;

import cn.acyou.framework.utils.redis.RedisUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-20 下午 12:25]
 **/
public class TestRedisCluster extends ApplicationTests {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testSetInCLuster(){
        redisUtils.set("aaa", "im aaa");
        redisUtils.set("bbb", "im bbb");
        System.out.println("设置成功");
        System.out.println(redisUtils.get("aaa"));
        System.out.println(redisUtils.get("bbb"));
    }
    @Test
    public void testGetInCLuster(){
        System.out.println(redisUtils.get("aaa"));
        System.out.println(redisUtils.get("bbb"));
    }
}
