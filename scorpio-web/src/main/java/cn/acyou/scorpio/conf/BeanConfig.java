package cn.acyou.scorpio.conf;

import cn.acyou.framework.commons.SnowFlakeWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 09:32]
 **/
@Configuration
public class BeanConfig {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public SnowFlakeWorker snowFlakeWorker() {
        return new SnowFlakeWorker();
    }
}
