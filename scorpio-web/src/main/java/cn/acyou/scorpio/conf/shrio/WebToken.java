package cn.acyou.scorpio.conf.shrio;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lixiao
 * @date 2019/8/5 23:43
 */
public class WebToken implements AuthenticationToken {

    private String token;

    public WebToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
