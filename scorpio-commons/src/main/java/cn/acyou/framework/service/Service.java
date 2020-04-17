package cn.acyou.framework.service;

import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
public interface Service<T> {

    int insertSelective(T record);

    List<T> selectByIdList(Collection idList);

    int deleteByIdList(Collection idList);

    int insertList(List<T> recordList);

    int updateByPrimaryKeySelective(T record);


}
