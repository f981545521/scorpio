package cn.acyou.scorpio.aop;

import cn.acyou.framework.valid.EnhanceValidUtil;
import cn.acyou.framework.valid.annotation.ParamValid;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 1、filter，这是java的过滤器，和框架无关的，是所有过滤组件中最外层的，从粒度来说是最大的。
 * 2、interceptor，spring框架的拦截器。
 * 3、aspect，可以自定义要切入的类甚至再细的方法，粒度最小。加个注解用效果更佳。
 */
@Slf4j
@Aspect
@Component
public class ParameterValidateAspect {

    /**
     * 定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码.
     * 使用 @Pointcut 来声明切入点表达式.
     * 后面的其他通知直接使用方法名来引用当前的切入点表达式.
     * <p>
     * 所有Controller 的请求
     */
    @Pointcut("execution(* cn.acyou.scorpio.controller.*..*(..))")
    public void parameterValid() {

    }

    @Before("parameterValid()")
    public void before(JoinPoint jp) {
        log.info("执行参数校验.....");
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
        Object[] args = jp.getArgs();
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof ParamValid) {
                    Object paramValue = args[paramIndex];
                    EnhanceValidUtil.valid(paramValue);
                }
                if (annotation instanceof RequestParam) {
                    Object paramValue = args[paramIndex];
                    paramsMap.put("RequestParam_" + paramIndex, paramValue);
                }
                if (annotation instanceof RequestBody) {
                    Object paramValue = args[paramIndex];
                    paramsMap.put("RequestBody_" + paramIndex, paramValue);
                }
                if (annotation instanceof PathVariable) {
                    Object paramValue = args[paramIndex];
                    paramsMap.put("PathVariable_" + paramIndex, paramValue);
                }
            }
        }
        System.out.println(JSON.toJSONString(paramsMap));
        log.info("执行参数完成!");
    }


}
