package cn.acyou.scorpio.interceptor.mybatis;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.utils.ReflectUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 配合tkMapper乐观锁检查
 *
 * MODIFIED_BY_ANOTHER_USER_MSG = "页面内容过期了,请刷新页面后再继续操作!"
 * REMOVED_BY_ANOTHER_USER_MSG = "页面数据已经被删除,请稍后刷新再试!"
 * LOCKED_BY_ANOTHER_USER_MSG = "页面数据被锁定了,请稍后刷新再试!"
 * @author youfang
 * @version [1.0.0, 2020/7/7]
 **/
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class OptimistLockCheckInterceptor implements Interceptor {
    private Logger log = LoggerFactory.getLogger(OptimistLockCheckInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()) {
            if (invocation.getArgs().length > 1) {
                Object parameterObject = invocation.getArgs()[1];
                Class<?> entityClass = parameterObject.getClass();
                //boolean annotationPresent = entityClass.isAnnotationPresent(Table.class);
                Table annotation = entityClass.getAnnotation(Table.class);
                if (annotation != null){
                    String tableName = annotation.name();
                    Field idField = ReflectUtils.recursiveFieldFinder(entityClass, Id.class);
                    Field versionEntityField = ReflectUtils.recursiveFieldFinder(entityClass, Version.class);
                    if (idField != null && versionEntityField != null){
                        String idColumnName = idField.getAnnotation(Column.class).name();
                        String versionColumnName = versionEntityField.getAnnotation(Column.class).name();

                        Object idColumnValue = ReflectUtils.getFieldValue(parameterObject, versionEntityField)
                        Object versionColumnValue = ReflectUtils.getFieldValue(parameterObject, idField);
                        if (!idColumnName.equals("") && !versionColumnName.equals("") && idColumnValue != null && versionColumnValue != null) {
                            Executor executor = (Executor) invocation.getTarget();
                            Connection connection = executor.getTransaction().getConnection();
                            Object databaseVersion = null;
                            //select version = 35 from table WHERE id = 1;  result -  正确：1，错误：0，不存在：null
                            PreparedStatement stmt = connection.prepareStatement(String.format("SELECT %s = ? FROM %s WHERE %s = ?", versionColumnName, tableName, idColumnName));
                            stmt.setObject(1, idColumnValue);
                            stmt.setObject(2, versionColumnValue);
                            log.info(stmt.toString());
                            stmt.execute();
                            ResultSet rs = stmt.getResultSet();
                            if (rs.first()) {
                                databaseVersion = rs.getObject(1);
                                rs.close();
                            }
                            stmt.close();
                            //adjust
                            if (databaseVersion == null){
                                throw new ServiceException("页面数据已经被删除,请稍后刷新再试!");
                            }
                            if (databaseVersion.equals(0)){
                                throw new ServiceException("页面内容过期了,请刷新页面后再继续操作!");
                            }
                        }
                    }
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
