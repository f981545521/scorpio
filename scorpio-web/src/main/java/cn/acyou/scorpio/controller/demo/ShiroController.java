package cn.acyou.scorpio.controller.demo;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.mapper.system.entity.Student;
import cn.acyou.scorpio.service.demo.StudentService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-20 下午 09:05]
 **/
@RestController
@RequestMapping("/shiro")
@Api(description = "ShiroController", tags = "Shiro权限管理测试")
public class ShiroController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/loginPage")
    public Result<String> defaultLogin() {
        return Result.success("首页");
    }

    @PostMapping(value = "/login")
    public Result<String> login() {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123");
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.error("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Result.error("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }
        if (subject.isAuthenticated()) {
            return Result.success("登录成功");
        } else {
            token.clear();
            return Result.error("登录失败");
        }
    }

    @RequiresPermissions("student:list")
    @PostMapping("/listStudents")
    public Result<List<Student>> listStudents() {
        List<Student> students = studentService.selectAll();
        return Result.success(students);
    }

    @RequiresPermissions("student:get")
    @PostMapping("/getStudent")
    public Result<Student> getStudent(Long id) {
        Student student = studentService.selectByPrimaryKey(id);
        return Result.success(student);
    }

    @RequiresRoles("developer")
    @PostMapping("/listStudentsForDeveloper")
    public Result<List<Student>> listStudentsForDeveloper() {
        List<Student> students = studentService.selectAll();
        return Result.success(students);
    }


}
