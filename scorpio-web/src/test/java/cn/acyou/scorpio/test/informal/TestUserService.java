package cn.acyou.scorpio.test.informal;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.entity.User;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import cn.acyou.scorpio.system.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-17 下午 08:27]
 **/
public class TestUserService extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testInsertListSelective(){
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setAge(88);
        student.setName("GG");
        students.add(student);
        Student student2 = new Student();
        student2.setAge(88);
        students.add(student2);
        studentMapper.insertListSelective(students);
        System.out.println(students);
    }
    @Test
    public void test2(){
        List<Student> studentsByAge = studentMapper.getStudentsByAge(22);
        System.out.println(studentsByAge);
    }
    @Test
    public void test1(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setRoleId(1L);
            user.setUserName(RandomUtil.randomUserName());
            user.setSignature("");
            user.setSex(RandomUtil.random01());
            user.setPhone(RandomUtil.randomTelephone());
            user.setAge(RandomUtil.randomAge());
            user.setBirthday(DateUtil.randomDate());
            user.setPassword("1");
            user.setStatus(1);
            user.setCreateUser(1L);
            user.setCreateTime(new Date());
            users.add(user);
        }
        userMapper.insertList(users);
    }
}
