package cn.acyou.framework.mapper.tkMapper;

import cn.acyou.framework.mapper.tkMapper.provide.CommonMapperProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;
import java.util.List;

/**
 * 公共Mapper接口
 *
 * @param <T> 不能为空
 * @author youfang
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface CommonMapper<T> {

    /**
     * 获取下一个排序值
     * <p>
     * 实体中必须有@Id注解
     *
     * @return 下一个排序值（主键count + 1）
     */
    @SelectProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    Integer getNextSortNumber();
    /**
     * 根据主键List进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 如 List<Long>
     * @return 根据主键ID查询
     */
    @SelectProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    List<T> selectByPrimaryKeyList(Collection idList);
    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 如 List<Long>
     * @return 影响行数
     */
    @DeleteProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKeyList(Collection idList);

    /**
     * 批量更新
     *
     * @param recordList 修改记录
     */
    @UpdateProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    int updateListSelective(Collection<T> recordList);

    /**
     * 根据主键更新属性不为null的值 （根据注解忽略）
     * cn.acyou.framework.mapper.tkMapper.annotation.SelectiveIgnore
     *     在属性字段上加上注解，不会忽略null值
     *     - @SelectiveIgnore
     * @param record
     * @return
     */
    @UpdateProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelectiveCustom(T record);
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * insert ignore into
     * @param record
     * @return
     */
    @InsertProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    int insertIgnoreSelective(T record);
}