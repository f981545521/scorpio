package cn.acyou.framework.service;

/**
 * 顶级 Service
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
public interface Service<T> {

    int insertSelective(T record);

}
