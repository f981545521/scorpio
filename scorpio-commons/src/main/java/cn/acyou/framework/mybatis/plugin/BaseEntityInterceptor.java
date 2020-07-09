package cn.acyou.framework.mybatis.plugin;

import cn.acyou.framework.model.BaseEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.Properties;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/7]
 **/
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class BaseEntityInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (invocation.getArgs().length > 1) {
            Object parameterObject = invocation.getArgs()[1];
            if (parameterObject instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) parameterObject;
                if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType()) {
                    baseEntity.setCreateUser(100L);
                    baseEntity.setCreateTime(new Date());
                }
                if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()) {
                    baseEntity.setLastUpdateUser(200L);
                    baseEntity.setLastUpdateTime(new Date());
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
