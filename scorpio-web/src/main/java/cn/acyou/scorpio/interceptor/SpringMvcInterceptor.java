package cn.acyou.scorpio.interceptor;

import cn.acyou.framework.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youfang
 * @version [1.0.0, 2018-07-26 下午 03:21]
 **/
@Slf4j
public class SpringMvcInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("———————————————————————————— SpringMvcInterceptor start ————————————————————————————");
        String ua = request.getHeader("User-Agent");
        log.info("———————————————————————————— SpringMvcInterceptor 获取到User-Agent : "+ua+" ————————————————————————————");

        boolean weChat = UserAgentUtil.isWeChat(request);
        boolean ie = UserAgentUtil.isIEBrowser(request);
        boolean mb = UserAgentUtil.isMobile(request);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("———————————————————————————— SpringMvcInterceptor end ————————————————————————————");
        super.afterCompletion(request, response, handler, ex);
    }
}
