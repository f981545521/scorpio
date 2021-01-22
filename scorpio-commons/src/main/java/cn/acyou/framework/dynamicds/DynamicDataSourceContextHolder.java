package cn.acyou.framework.dynamicds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多数据源切换
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
public class DynamicDataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源变量
     *
     * @param dataSourceType 数据源类型
     */
    public static void setDataSourceType(DataSourceType dataSourceType) {
        log.info("切换数据源到 -> {}", dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源变量
     *
     * @return 数据源
     */
    public static DataSourceType getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
