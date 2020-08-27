package cn.acyou.scorpio.controller.demo;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.system.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/2]
 **/
@RestController
@RequestMapping("rest")
public class RestTemplateController {
    @GetMapping("test1")
    public String test1() {
        return "ok";
    }

    @GetMapping("test2")
    public String test2(String name) {
        System.out.println("test2 参数name:" + name);
        return name;
    }
    @GetMapping("test3")
    public Result test3(String name) {
        System.out.println("test3 参数name:" + name);
        return Result.success();
    }

    @PostMapping("testPost1")
    public String testPost1(String name) {
        System.out.println("testPost1 参数name:" + name);
        return name;
    }

    @PostMapping(value = "testPost2")
    public Result<String> testPost2(String name) {
        System.out.println("testPost2 参数name:" + name);
        return Result.success(name);
    }
    @PostMapping(value = "testPost3")
    public Result<List<Student>> testPost3(String name) {
        System.out.println("testPost3 参数name:" + name);
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName(name);
        student.setAge(22);
        students.add(student);
        return Result.success(students);
    }




}
