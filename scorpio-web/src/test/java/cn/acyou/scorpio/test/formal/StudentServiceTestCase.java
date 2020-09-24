package cn.acyou.scorpio.test.formal;

import cn.acyou.scorpio.test.formal.base.ApplicationBaseTests;
import cn.acyou.scorpio.service.demo.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * @author youfang
 * @version [1.0.0, 2020/9/24]
 **/
public class StudentServiceTestCase extends ApplicationBaseTests {

    @Autowired
    private StudentService studentService;

    @Test
    @Rollback
    public void test1(){
        studentService.addAStudent();
        System.out.println("执行了测试用例");
    }
}
