package cn.acyou.scorpio.service.slave.impl;

import cn.acyou.framework.dynamicds.DS;
import cn.acyou.framework.dynamicds.DataSourceType;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.scorpio.salve.entity.StudentSlave;
import cn.acyou.scorpio.salve.mapper.StudentSlaveMapper;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.service.slave.StudentSlaveService;
import cn.acyou.scorpio.system.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentSlaveServiceImpl extends ServiceImpl<StudentSlaveMapper, StudentSlave> implements StudentSlaveService {
    @Autowired
    private StudentService studentService;
    /**
     * 查询全部结果
     *
     * @return 查询结果
     */
    @Override
    public List<StudentSlave> selectAll() {
        List<StudentSlave> studentSlaves = thisService().selectStudents();
        List<Student> students = studentService.selectAll();
        System.out.println(studentSlaves);
        System.out.println(students);
        return studentSlaves;
    }

    private StudentSlaveService thisService(){
        return (StudentSlaveService) AopContext.currentProxy();
    }

    @Override
    @DS(value = DataSourceType.SLAVE)
    public List<StudentSlave> selectStudents() {
        return baseMapper.selectAll();
    }
}
