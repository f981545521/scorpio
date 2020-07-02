package cn.acyou;

import cn.acyou.scorpio.mapper.system.entity.Student;
import cn.acyou.scorpio.service.demo.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/2]
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
    @Autowired
    private StudentService studentService;
    @Test
    public void test1(){
        System.out.println("ok");
        List<Student> students = studentService.selectAll();
        System.out.println(students);
    }
}
