package cn.acyou.scorpio.service.slave.impl;

import cn.acyou.framework.dynamicds.DataSource;
import cn.acyou.framework.dynamicds.DataSourceType;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.scorpio.salve.entity.StudentSlave;
import cn.acyou.scorpio.salve.mapper.StudentSlaveMapper;
import cn.acyou.scorpio.service.slave.StudentSlaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
@Slf4j
@Service
public class StudentSlaveServiceImpl extends ServiceImpl<StudentSlaveMapper, StudentSlave> implements StudentSlaveService {
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<StudentSlave> selectStudents() {
        return baseMapper.selectAll();
    }
}
