package cn.acyou.framework.service;

import cn.acyou.framework.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 顶级 Service 实现，未完成
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class ServiceImpl<M extends Mapper<T>, T> implements Service<T>{

    private final static Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    protected M mapper;

    @Override
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }
}
