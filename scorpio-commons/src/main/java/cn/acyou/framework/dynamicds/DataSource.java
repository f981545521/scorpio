package cn.acyou.framework.dynamicds;

import java.lang.annotation.*;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;
}
