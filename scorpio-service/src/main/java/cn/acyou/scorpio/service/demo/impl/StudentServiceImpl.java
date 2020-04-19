package cn.acyou.scorpio.service.demo.impl;

import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.mapper.system.entity.Student;
import cn.acyou.scorpio.mapper.system.mapper.StudentMapper;
import cn.acyou.scorpio.service.demo.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-17 下午 08:56]
 **/
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void studentTest() {
        Example example = builderExample()
                .where(Sqls.custom()
                        .andGreaterThan("birth", DateUtil.parseDate("2020-04-01")))
                .orderByDesc("age")
                .build();
        List<Student> students = baseMapper.selectByExample(example);
        System.out.println(students);
        super.updateByPrimaryKeySelective(students.get(0));
        this.updateByPrimaryKeySelective(students.get(0));
        Example build = Condition.builder(Student.class).build();
    }

    @Override
    public void addAStudent() {
        Student student = new Student();
        student.setName(RandomUtil.randomUserName());
        student.setAge(RandomUtil.randomAge());
        student.setBirth(new Date());
        baseMapper.insert(student);
        int i = 1/0;
    }
}
