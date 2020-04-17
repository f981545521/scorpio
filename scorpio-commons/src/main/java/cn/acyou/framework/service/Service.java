package cn.acyou.framework.service;

import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
public interface Service<T> {

    int insert(T record);

    int insertSelective(T record);

    int insertList(List<T> recordList);

    List<T> selectPrimaryKeyList(Collection idList);

    int deleteByPrimaryKeyList(Collection idList);

    int updateByPrimaryKeySelective(T record);

}
