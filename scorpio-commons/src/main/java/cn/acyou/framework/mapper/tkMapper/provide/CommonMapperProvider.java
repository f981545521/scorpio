package cn.acyou.framework.mapper.tkMapper.provide;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * CommonMapperProvider实现类，自己添加的公共方法Mapper
 *
 * @author liuzh
 */
public class CommonMapperProvider extends MapperTemplate {

    public CommonMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询当前最大的SortNum  + 1
     * <p>
     * 实体中必须有@Id注解
     *
     * @param ms MappedStatement
     * @return 下一个排序值
     */
    public String getNextSortNumber(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append("SELECT count(").append(column.getColumn()).append(") + 1 ");
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            return sql.toString();
        } else {
            throw new MapperException("继承 getNextSortNumber 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }

    }

}
