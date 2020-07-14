package cn.acyou.scorpio.controller.sys;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.model.PageData;
import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.framework.utils.TreeUtil;
import cn.acyou.framework.utils.redis.RedisUtils;
import cn.acyou.framework.valid.annotation.BaseValid;
import cn.acyou.framework.valid.annotation.ParamValid;
import cn.acyou.scorpio.dto.demo.MenuVo;
import cn.acyou.scorpio.dto.demo.StudentSo;
import cn.acyou.scorpio.dto.demo.StudentVo;
import cn.acyou.scorpio.dto.task.TaskErrorEnum;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.service.demo.StudentService2;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    private WebMvcRequestHandlerProvider mvcRequestHandlerProvider;

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
        PageHelper.startPage(pageNum, Integer.MAX_VALUE);
        List<Student> students = studentMapper.selectAll();
        //List<Student> selectByIdList = studentMapper.selectPrimaryKeyList(Lists.newArrayList(5,6,7));
        //for (Student student: selectByIdList){
        //    student.setName(RandomUtil.randomUserName());
        //}
        //int c = studentMapper.updateListSelective(selectByIdList);
        //System.out.println(c);

        if (pageNum == 10){
            throw new ServiceException(TaskErrorEnum.E_200017);
        }
        PageData<Student> convert = PageData.convert(students);
        System.out.println(convert);
        return Result.success(convert);
    }

    @RequestMapping(value = "studentsPage", method = {RequestMethod.GET})
    @ApiOperation("测试分页")
    public Result<PageData<StudentVo>> studentsPage(Integer pageNum, Integer pageSize) {

        //PageData<Student> convert2 =  PageData.startPage(pageNum, pageSize).selectMapper(studentService.selectAll());
        PageData<StudentVo> convertType =  PageData.startPage(pageNum, pageSize).selectMapper(studentService.selectAll(), StudentVo.class);

        return Result.success(convertType);
    }

    @RequestMapping(value = "students2", method = {RequestMethod.GET})
    @ApiOperation("测试分页")
    public Result<List<Student>> selectStudent2() {
        List<Student> students = studentService.selectByProperties("age", "38");
        List<Integer> ar = new ArrayList<>();
        return Result.success(students);
    }
    @RequestMapping(value = "cacheGetStudent", method = {RequestMethod.GET})
    @ApiOperation("测试增加缓存")
    public Result<?> cacheGetStudent(Integer id) {
        Student student = studentService.selectByPrimaryKey(id);
        return Result.success(student);
    }
    @RequestMapping(value = "flushCacheStudent", method = {RequestMethod.GET})
    @ApiOperation("测试刷新缓存")
    public Result<?> flushCacheStudent(Integer id) {
        studentService.flushCache(id);
        return Result.success();
    }
    @RequestMapping(value = "searchStudent", method = {RequestMethod.GET})
    @ApiOperation("测试SO")
    public Result<?> searchStudent(StudentSo studentSo) {
        PageData<Student> name = PageData.startPage(studentSo)
                .selectMapper(studentService.selectByProperties("age", studentSo.getAge()));

        return Result.success(name);
    }

    @RequestMapping(value = "testTreeUtil", method = {RequestMethod.GET})
    @ApiOperation("测试=TreeUtil")
    public Result<?> testTreeUtil() {
        List<MenuVo> menuVoList = new ArrayList<>();
        menuVoList.add(new MenuVo(1L, 0L, "顶级目录1"));
        menuVoList.add(new MenuVo(2L, 0L, "顶级目录2"));
        menuVoList.add(new MenuVo(3L, 1L, "目录3"));
        menuVoList.add(new MenuVo(4L, 1L, "目录4"));
        menuVoList.add(new MenuVo(5L, 1L, "目录5"));
        menuVoList.add(new MenuVo(6L, 2L, "目录6"));
        menuVoList.add(new MenuVo(7L, 2L, "目录7"));
        menuVoList.add(new MenuVo(8L, 4L, "目录8"));

        List<MenuVo> menuVoList1 = TreeUtil.generateTrees(menuVoList);
        return Result.success(menuVoList1);
    }

    @RequestMapping(value = "checkNotNullAnnotation", method = {RequestMethod.GET})
    @ApiOperation("检查NotNull注解的有效性")
    public Result<Void> checkNotNullAnnotation(@ParamValid @BaseValid(notNull = true) String name){
        Assert.notNull(name, "name不呢为空！");
        System.out.println("检查NotNull注解的有效性" + name);
        return Result.success();
    }

    @RequestMapping(value = "swaggerResult", method = {RequestMethod.GET})
    @ApiOperation("Swagger扫描结果")
    public Result<Void> swaggerResult(){
        List<RequestHandler> requestHandlers = mvcRequestHandlerProvider.requestHandlers();
        return Result.success();
    }
    @RequestMapping(value = "cookieTest", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("cookie测试")
    public Result<Void> cookieTest(HttpServletResponse response){
        Cookie cookie = new Cookie("scorpio-test", RandomUtil.createRandomStr(10));
        cookie.setHttpOnly(true);
        //过期时间：1年
        cookie.setMaxAge(365 * 24 * 60 * 60);
        cookie.setPath("/");


        response.addCookie(cookie);
        System.out.println("ok");
        return Result.success();
    }

    @RequestMapping(value = "cookieTest2", method = {RequestMethod.POST})
    @ApiOperation("cookie测试")
    public Result<Void> cookieTest2(){
        return Result.success();
    }
    @RequestMapping(value = "voParamTest", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("复杂参数测试")
    public Result<Void> voParamTest(@RequestBody StudentVo studentVo){
        System.out.println(studentVo);
        return Result.success();
    }
    @RequestMapping(value = "singleParamTest", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("单个参数测试")
    public Result<Void> singleParamTest(@RequestBody String name){
        System.out.println(name);
        if ("caocao".equals(name)){
            throw new ServiceException("name 不能是 caocao");
        }
        return Result.success();
    }



}
