package cn.acyou.scorpio.interceptor;

import cn.acyou.framework.utils.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

    /**
     * 获取请求参数
     * 这里虽然可以获取到参数，不过随后的参数绑定却会出错。。。。
     *
     * @param request 请求
     * @return {@link String}
     */
    public String getRequestParamsStr(HttpServletRequest request){
        String method = request.getMethod();
        try {
            if (HttpMethod.GET.name() .equals(method)){
                Map<String, String[]> parameterMap = request.getParameterMap();
                return JSON.toJSONString(parameterMap);
            }
            if (HttpMethod.POST.name() .equals(method)){
                return IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
