package cn.acyou.framework.service;

import cn.acyou.framework.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service 实现，未完成
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class ServiceImpl<M extends Mapper<T>, T> implements Service<T>{

    private final static Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    protected M baseMapper;

    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public List<T> selectByIdList(Collection idList){
        return baseMapper.selectByIdList(idList);
    }

    @Override
    public int deleteByIdList(Collection idList){
        return baseMapper.deleteByIdList(idList);
    }

    @Override
    public int insertList(List<T> recordList){
        return baseMapper.insertList(recordList);
    }

    @Override
    public int updateByPrimaryKeySelective(T record){
        return baseMapper.updateByPrimaryKeySelective(record);
    }

}
