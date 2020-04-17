package cn.acyou.scorpio.demo.impl;

import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.scorpio.demo.StudentService;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

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
}
