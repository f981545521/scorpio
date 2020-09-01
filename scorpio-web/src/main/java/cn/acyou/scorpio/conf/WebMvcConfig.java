package cn.acyou.scorpio.conf;

import cn.acyou.scorpio.interceptor.SpringMvcInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 根据类型注入一个List，只要Spring中有这个类型的都加到这个集合中
     */
    @Autowired(required = false)
    private List<HandlerInterceptor> interceptorList = Collections.emptyList();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (HandlerInterceptor handlerInterceptor : interceptorList) {
            if (handlerInterceptor instanceof SpringMvcInterceptor){
                List<String> excludePaths = new ArrayList<>();
                excludePaths.add("/static/*");
                /* Swagger */
                excludePaths.add("/swagger-ui.html");
                excludePaths.add("/swagger-resources/*");
                excludePaths.add("/v2/*");
                //registry.addInterceptor(handlerInterceptor).excludePathPatterns(excludePaths);
            }else {
                registry.addInterceptor(handlerInterceptor);
            }
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //静态资源文件映射，然后就可以直接访问
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

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
     * 使用CORS解决解决跨域问题（必须）
     * 使用此方法配置之后再使用自定义拦截器时跨域相关配置就会失效。
     * 导致ControllerAdvice中的HttpRequestMethodNotSupportedException不能被拦截处理。
     */
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**")
    //            .allowedOrigins("*")
    //            .allowCredentials(true)
    //            //注意：此处不能加"OPTIONS"，会导致页面发起options请求
    //            .allowedMethods("GET", "POST", "DELETE", "PUT");
    //}

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.addAllowedHeader("*");// 允许任何的head头部
        corsConfiguration.addAllowedOrigin("*");// 允许任何域名使用
        corsConfiguration.addAllowedMethod("*");// 允许任何的请求方法
        corsConfiguration.setAllowCredentials(true);// 允许发送Cookie
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
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
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }


}
