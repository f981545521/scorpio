package cn.acyou.framework.service;

import cn.acyou.framework.mapper.Mapper;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    private Class<T> claz;

    public ServiceImpl(){
        TypeToken<T> poType = new TypeToken<T>(getClass()) {};
        claz = (Class<T>) poType.getRawType();
    }

    /**
     * 构建查询Example
     * <pre>
     *          Example example = builderExample()
     *              .where(Sqls.custom()
     *                      .andGreaterThan("birth", DateUtil.parseDate("2020-04-01")))
     *              .orderByDesc("age")
     *              .build();
     *         List<Student> students = baseMapper.selectByExample(example);
     * </pre>
     * @return
     */
    public Example.Builder builderExample(){
        return Example.builder(claz);
    }

    @Override
    public int insert(T record){
        return baseMapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public int insertList(List<T> recordList){
        return baseMapper.insertList(recordList);
    }

    @Override
    public List<T> selectPrimaryKeyList(Collection idList){
        return baseMapper.selectPrimaryKeyList(idList);
    }

    @Override
    public int deleteByPrimaryKeyList(Collection idList){
        return baseMapper.deleteByPrimaryKeyList(idList);
    }

    @Override
    public int updateByPrimaryKeySelective(T record){
        return baseMapper.updateByPrimaryKeySelective(record);
    }

}
