package cn.acyou.scorpio.controller;

import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.redis.RedisUtils;
import cn.acyou.scorpio.entity.Student;
import cn.acyou.scorpio.mapper.StudentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author youfang
 * @date 2018-04-15 下午 09:38
 **/
@Slf4j
@RestController
@RequestMapping("/student")
@Api(value = "学生", description = "学生的增删改查", tags = "学生接口")
public class StudentController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(value = "students", method = {RequestMethod.GET})
    @ApiOperation("增加一个学生")
    @ResponseBody
    public Result<List<Student>> selectStudent() {
        List<Student> students = studentMapper.selectAll();
        log.info("查询学生");
        return Result.success(students);
    }


}
