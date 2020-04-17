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
     * 查询当前记录数 + 1
     *
     * 实体中必须有@Id注解
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
    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     */
    public String selectByIdList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append(" where ");
            sql.append(column.getColumn());
            sql.append(" in ");
            sql.append("<foreach collection=\"list\" item=\"id\" open=\"(\" separator=\",\" close=\")\" >");
            sql.append("#{id}");
            sql.append("</foreach>");
        } else {
            throw new MapperException("继承 selectByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return sql.toString();
    }
    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     */
    public String deleteByIdList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append(" where ");
            sql.append(column.getColumn());
            sql.append(" in ");
            sql.append("<foreach collection=\"list\" item=\"id\" open=\"(\" separator=\",\" close=\")\" >");
            sql.append("#{id}");
            sql.append("</foreach>");
        } else {
            throw new MapperException("继承 deleteByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return sql.toString();
    }
    /**
     * 根据主键批量更新
     */
    public String updateListSelective(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append("<if test=\"list==null or list.size()==0\">");
            sql.append("    select 0 from dual");
            sql.append("</if>");
            sql.append("<foreach collection=\"list\" item=\"it\">");
            sql.append("    update " + tableName(entityClass));
            sql.append(SqlHelper.updateSetColumns(entityClass, "it", true, isNotEmpty()));
            sql.append("    WHERE " + column.getColumn() + " = #{it." + column.getColumn() + "};");
            sql.append("</foreach>");
        } else {
            throw new MapperException("继承 deleteByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return sql.toString();
    }

}
