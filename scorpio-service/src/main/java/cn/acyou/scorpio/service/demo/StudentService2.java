package cn.acyou.scorpio.service.demo;

import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.mapper.system.entity.Student;
import cn.acyou.scorpio.mapper.system.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020/4/20]
 **/
@Service
public class StudentService2 {

    @Autowired
    private StudentMapper studentMapper;

    public void addAStudent() {
        Student student = new Student();
        student.setName(RandomUtil.randomUserName());
        student.setAge(RandomUtil.randomAge());
        student.setBirth(new Date());
        studentMapper.insert(student);
        int i = 1/0;
    }
}
