package cn.acyou.scorpio.conf;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author youfang
 * @version [1.0.0, 2020/9/2]
 **/
@Slf4j
@Configuration
public class WxServiceConfig {

    @Autowired
    private WxMaProperties wxMaProperties;

    @Bean
    public WxMaService wxMaService(){
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMaProperties.getAppid());
        config.setSecret(wxMaProperties.getSecret());
        config.setToken(wxMaProperties.getToken());
        config.setAesKey(wxMaProperties.getAesKey());
        config.setMsgDataFormat(wxMaProperties.getMsgDataFormat());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        log.info("初始化WxMaService 成功");
        return service;
    }

}
