package cn.acyou.scorpio.aop;

import cn.acyou.framework.dynamicds.DS;
import cn.acyou.framework.dynamicds.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源且切换Aspect
 * 支持方法/类
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(cn.acyou.framework.dynamicds.DS) || @within(cn.acyou.framework.dynamicds.DS)")
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DS dataSource = method.getAnnotation(DS.class);
        if (dataSource == null) {
            //方法上没有注解，就获取类上的注解
            Class<?> cls = point.getSignature().getDeclaringType();
            dataSource = cls.getAnnotation(DS.class);
        }
        if (dataSource != null) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }
}
