package cn.acyou.scorpio.test.formal;

import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.test.formal.base.ApplicationBaseTests;
import cn.acyou.scorpio.service.demo.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020/9/24]
 **/
public class StudentServiceTestCase extends ApplicationBaseTests {

    @Autowired
    private StudentService studentService;

    @Test
    @Rollback//加入注解 或者 默认 会回滚
    public void testRollback(){
        studentService.addAStudent();
        System.out.println("执行了测试用例 testRollback");
        List<Student> students = studentService.selectAll();
        //initDb.sql 里面4条记录 + 刚添加的一条记录都会被查出
        Assert.assertEquals(students.size(), 5);
        System.out.println(students);
    }
    @Test
    @Commit//如果需要提交事务，使用此注解
    public void testCommit(){
        studentService.addAStudent();
        System.out.println("执行了测试用例 testCommit");
    }


    @Test
//  @Commit
    public void testSQl(){
        System.out.println("执行测试用例 testSQl...");
        List<Student> students = studentService.selectAll();
        System.out.println(students);
    }
}
