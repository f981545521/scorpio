package cn.acyou.framework.mapper.tkMapper;

import cn.acyou.framework.mapper.tkMapper.provide.CommonMapperProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;

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
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 如 List<Long>
     * @return 根据主键ID查询
     */
    @SelectProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    List<T> selectByIdList(Collection idList);
    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 如 List<Long>
     * @return 影响行数
     */
    @DeleteProvider(type = CommonMapperProvider.class, method = "dynamicSQL")
    int deleteByIdList(Collection idList);

}