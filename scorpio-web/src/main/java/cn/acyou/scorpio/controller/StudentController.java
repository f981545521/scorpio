package cn.acyou.scorpio.controller;

import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.framework.utils.redis.RedisUtils;
import cn.acyou.scorpio.demo.StudentService;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(value = "testExample", method = {RequestMethod.GET})
    @ApiOperation("testExample")
    public Result<Void> testExample(){
        studentService.studentTest();
        return Result.success();
    }

    @RequestMapping(value = "students", method = {RequestMethod.GET})
    @ApiOperation("增加一个学生")
    public Result<Void> selectStudent() {
        List<Student> selectByIdList = studentMapper.selectPrimaryKeyList(Lists.newArrayList(5,6,7));
        for (Student student: selectByIdList){
            student.setName(RandomUtil.randomUserName());
        }
        int c = studentMapper.updateListSelective(selectByIdList);
        System.out.println(c);
        return Result.success();
    }


}
