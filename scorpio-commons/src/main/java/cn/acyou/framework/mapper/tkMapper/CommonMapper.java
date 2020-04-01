package cn.acyou.framework.mapper.tkMapper;

import cn.acyou.framework.mapper.tkMapper.provide.CommonMapperProvider;
import org.apache.ibatis.annotations.SelectProvider;

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

}