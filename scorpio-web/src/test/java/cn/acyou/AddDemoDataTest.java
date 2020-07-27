package cn.acyou;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.system.entity.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-27 下午 10:14]
 **/
public class AddDemoDataTest extends ApplicationTests {
    @Autowired
    private StudentService studentService;
    @Test
    public void prepareData(){
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            Student student = new Student();
            student.setName(RandomUtil.randomUserName());
            student.setAge(RandomUtil.randomAge());
            student.setBirth(DateUtil.randomDate());
            students.add(student);
        }
        studentService.insertList(students);
        System.out.println("———————— over ——————————");
    }
    @Test
    public void test1(){
        System.out.println("ok");
        List<Student> students = studentService.selectAll();
        System.out.println(students);
    }
}
