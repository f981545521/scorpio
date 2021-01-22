package cn.acyou.scorpio.conf;

import cn.acyou.framework.dynamicds.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 根据Key获取数据源的信息
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
