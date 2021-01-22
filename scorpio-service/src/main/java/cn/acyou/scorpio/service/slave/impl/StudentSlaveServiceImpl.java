package cn.acyou.scorpio.service.slave.impl;

import cn.acyou.framework.dynamicds.DS;
import cn.acyou.framework.dynamicds.DataSourceType;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.scorpio.salve.entity.StudentSlave;
import cn.acyou.scorpio.salve.mapper.StudentSlaveMapper;
import cn.acyou.scorpio.service.slave.StudentSlaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注意：
 * aspectJ进行AOP的对象必须是自身有的方法会被Proxy，但是如果继承父类，那么此方法不会被Proxy。比较愚钝的办法是在子类override父类方法，然后调用super执行。
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
@Slf4j
@Service
@DS(value = DataSourceType.SLAVE)
public class StudentSlaveServiceImpl extends ServiceImpl<StudentSlaveMapper, StudentSlave> implements StudentSlaveService {
    /**
     * 查询全部结果
     *
     * @return 查询结果
     */
    @Override
    public List<StudentSlave> selectAll() {
        return super.selectAll();
    }

    @Override
    @DS(value = DataSourceType.SLAVE)
    public List<StudentSlave> selectStudents() {
        return baseMapper.selectAll();
    }
}
