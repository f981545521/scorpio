package cn.acyou.scorpio.conf;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.exception.UnLoginException;
import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.interceptor.SpringMvcInterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 根据类型注入一个List，只要Spring中有这个类型的都加到这个集合中
     */
    @Autowired(required = false)
    private List<HandlerInterceptor> interceptorList = Collections.emptyList();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (HandlerInterceptor handlerInterceptor : interceptorList) {
            registry.addInterceptor(handlerInterceptor);
        }
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


    /**
     * springboot拦截器提取@Value属性值时为空的解决方案。
     * interceptor默认是不被spring context掌管的。所以还添加@bean ，加入的spring 容器下，才可以读取的spring容器内的变量值
     * 通过Profile注解可以实现，除了某个环境加载Bean的问题
     * <p>
     * Bean 的加载问题：
     * Application启动后会自动扫描同级以及子级目录中的注解自动装备bean，需要加上@SpringBootApplication。
     * 如果使用mybatis，在dao层接口上使用@Repository是扫描不出来的，需要使用@Mapper才行。
     */
    @Bean
    public SpringMvcInterceptor springMvcInterceptor() {
        return new SpringMvcInterceptor();
    }


    /**
     * 使用CORS解决解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
                Result<Object> resultInfo = Result.error();
                ModelAndView mv = new ModelAndView("error");
                Throwable t = Throwables.getRootCause(e);
                log.error("统一异常处理，" + e.getMessage());
                e.printStackTrace();
                if (t instanceof ServiceException) {
                    resultInfo.setCode(400);
                    resultInfo.setMessage(t.getMessage());
                } else if (t instanceof UnLoginException) {
                    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                        response.setHeader("REDIRECT", "REDIRECT");//告诉ajax我是重定向
                        //告诉ajax我重定向的路径
                        response.setHeader("CONTENTPATH", "/login");
                        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                    } else {
                        try {
                            response.sendRedirect("/login");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    try {
                        //打印堆栈日志到日志文件中
                        ByteArrayOutputStream buf = new ByteArrayOutputStream();
                        e.printStackTrace(new PrintWriter(buf, true));
                        buf.close();
                        resultInfo.setData(buf.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    resultInfo.setCode(400);
                    resultInfo.setMessage("喔呦，程序奔溃咯！");
                }
                //响应Ajax请求
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    responseResult(response, resultInfo);

                    //这里无论如何都要抛出ModelAndView 的，否则报错：
                    // getWriter() has already been called for this response
                    return mv;
                }
                mv.addObject("code", resultInfo.getCode());
                mv.addObject("message", resultInfo.getMessage());
                String requestUrl = request.getRequestURL().toString();
                mv.addObject("requestUrl", requestUrl);
                //打印堆栈日志到字符串
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                String expMessage = buf.toString();
                String expSimpleMessage = (expMessage.length() > 500) ? expMessage.substring(0, 500) : expMessage;
                try {
                    buf.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
                mv.addObject("expMessage", expSimpleMessage);
                return mv;//响应ModelAndView请求
            }
        });
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.setStatus(200);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(JSON.toJSONString(result));
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createFastJsonHttpMessageConverter());
    }

    private FastJsonHttpMessageConverter createFastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullNumberAsZero);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        return converter;
    }


}
