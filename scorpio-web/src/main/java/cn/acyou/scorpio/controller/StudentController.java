package cn.acyou.scorpio.controller;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.model.PageData;
import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.redis.RedisUtils;
import cn.acyou.scorpio.dto.task.TaskErrorEnum;
import cn.acyou.scorpio.mapper.system.entity.Student;
import cn.acyou.scorpio.mapper.system.mapper.StudentMapper;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.service.demo.StudentService2;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    private StudentService2 studentService2;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(value = "testUpdateCustom", method = {RequestMethod.GET})
    @ApiOperation("testUpdateCustom")
    public Result<Void> testUpdateCustom(){
        studentMapper.deleteLogicByPrimaryKeyList(Lists.newArrayList(1,2,3));
        return Result.success();
    }


    @RequestMapping(value = "testExample", method = {RequestMethod.GET})
    @ApiOperation("testExample")
    public Result<Void> testExample(){
        studentService.studentTest();
        return Result.success();
    }
    @RequestMapping(value = "addAStudent", method = {RequestMethod.GET})
    @ApiOperation("增加一个学生")
    public Result<Void> addAStudent(){
        studentService.addAStudent();
        studentService2.addAStudent();
        return Result.success();
    }

    @RequestMapping(value = "students", method = {RequestMethod.GET})
    @ApiOperation("测试分页")
    public Result<PageData<Student>> selectStudent(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> students = studentMapper.selectAll();
        //List<Student> selectByIdList = studentMapper.selectPrimaryKeyList(Lists.newArrayList(5,6,7));
        //for (Student student: selectByIdList){
        //    student.setName(RandomUtil.randomUserName());
        //}
        //int c = studentMapper.updateListSelective(selectByIdList);
        //System.out.println(c);

        if (pageNum == 1){
            throw new ServiceException(TaskErrorEnum.E_200017);
        }
        PageData<Student> convert = PageData.convert(students);
        System.out.println(convert);
        return Result.success(convert);
    }

    @RequestMapping(value = "students2", method = {RequestMethod.GET})
    @ApiOperation("测试分页")
    public Result<List<Student>> selectStudent2() {
        List<Student> students = studentService.selectByProperties("age", "38");
        List<Integer> ar = new ArrayList<>();
        return Result.success(students);
    }


}
