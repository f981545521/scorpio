package cn.acyou.scorpio.conf.flowable;

import cn.acyou.framework.commons.SnowFlakeWorker;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable配置
 * @author youfang
 * @version [1.0.0, 2020-5-13 下午 09:32]
 **/
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Autowired
    private SnowFlakeWorker snowFlakeWorker;

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
        engineConfiguration.setIdGenerator(() -> snowFlakeWorker.nextIdStr());
    }
}
