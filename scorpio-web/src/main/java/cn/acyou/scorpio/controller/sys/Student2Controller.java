package cn.acyou.scorpio.controller.sys;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.service.demo.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/10]
 **/
@Slf4j
@RestController
@RequestMapping("/student2")
@Api(value = "学生2", description = "学生的增删改查2", tags = "学生接口2")
public class Student2Controller {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "testInsertSelect")
    @ApiOperation("testInsertSelect")
    public Result<Void> testInsertSelect(){
        studentService.testInsertSelect();
        return Result.success();
    }
}
