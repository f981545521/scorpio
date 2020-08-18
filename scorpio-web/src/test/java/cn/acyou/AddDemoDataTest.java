package cn.acyou;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-27 下午 10:14]
 **/
public class AddDemoDataTest extends ApplicationTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void prepareStudentData() {
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
    public void testSelectAll() {
        System.out.println("ok");
        List<Student> students = studentService.selectAll();
        System.out.println(students);
    }
    @Test
    public void testRefidSql() {
        studentMapper.staticTest("yyyy-MM-dd ss");
        System.out.println("ok");
    }
    @Test
    public void test222() {
        Map<Integer, Student> integerStudentMap = studentMapper.selectAgeSameStudent();
        System.out.println(integerStudentMap);
    }
    @Test
    public void test2224() {
        studentService.testSelectAndUpdateAndSelect();
    }
}
