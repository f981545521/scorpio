package cn.acyou.scorpio.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author youfang
 * @version [1.0.0, 2020/9/2]
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {
    /**
     * 设置微信小程序的appid
     */
    private String appid;

    /**
     * 设置微信小程序的Secret
     */
    private String secret;

    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;
}
