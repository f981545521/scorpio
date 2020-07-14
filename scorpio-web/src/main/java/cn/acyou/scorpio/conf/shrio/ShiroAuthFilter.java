package cn.acyou.scorpio.conf.shrio;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * @author lixiao
 * @date 2019/8/5 23:46
 */
@Slf4j
public class ShiroAuthFilter extends BasicHttpAuthenticationFilter {
    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "X-Token";
    /**
     * 执行登录认证
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest hr = (HttpServletRequest) request;

        System.out.println("ShiroAuthFilter 执行登录认证---------->isAccessAllowed" + hr.getRequestURI());
        String token = ((HttpServletRequest) request).getHeader(TOKEN_HEADER_NAME);

        // 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 执行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        System.out.println("ShiroAuthFilter 执行登录认证---------->executeLogin");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN_HEADER_NAME);
        WebToken jwtToken = new WebToken(token);

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}
