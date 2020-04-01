package cn.acyou.framework.mapper;

import cn.acyou.framework.mapper.tkMapper.CommonMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;


/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 *
 * @author youfang
 * @date 2018-04-24 下午 07:03
 **/
public interface Mapper<T>
        extends
        BaseMapper<T>,
        SelectCountMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T>,
        ExampleMapper<T>,
        ConditionMapper<T>,
        CommonMapper<T> {
}
