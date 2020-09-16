package cn.acyou.scorpio.controller.sys;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.model.PageData;
import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.IdUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.framework.utils.TreeUtil;
import cn.acyou.framework.utils.redis.RedisLock;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author youfang
 * @date 2018-04-15 下午 09:38
 **/
@Slf4j
@RestController
@RequestMapping("/student")
@Api(value = "学生", description = "学生的增删改查", tags = "学生接口")
public class StudentController {

    @Autowired(required = false)
    private WebMvcRequestHandlerProvider mvcRequestHandlerProvider;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentService2 studentService2;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private IdUtil idUtil;

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
    @RequestMapping(value = "cookieTest", method = {RequestMethod.GET})
    @ApiOperation("cookie测试")
    public Result<Void> cookieTest(HttpServletResponse response){
        Cookie cookie = new Cookie("scorpio-test", RandomUtil.randomStr(10));
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
    @RequestMapping(value = "voParamTest", method = {RequestMethod.GET})
    @ApiOperation("复杂参数测试")
    public Result<Void> voParamTest(@RequestBody StudentVo studentVo){
        System.out.println(studentVo);
        return Result.success();
    }
    @RequestMapping(value = "singleParamTest", method = {RequestMethod.GET})
    @ApiOperation("单个参数测试")
    public Result<Void> singleParamTest(@RequestBody String name){
        System.out.println(name);
        if ("caocao".equals(name)){
            throw new ServiceException("name 不能是 caocao");
        }
        return Result.success();
    }

    @RequestMapping(value = "insertAndThrowException", method = {RequestMethod.GET})
    @ApiOperation("插入并抛出异常会回滚吗")
    public Result<Void> insertAndThrowException(){
        studentService.addInsertAndThrowException();
        return Result.success();
    }
    int count = 100;
    @RequestMapping(value = "redisLock", method = {RequestMethod.GET})
    @ApiOperation("redis分布式锁测试代码")
    public Result<Void> redisLock() throws InterruptedException {
        int clientcount = 100;
        CountDownLatch countDownLatch = new CountDownLatch(clientcount);

        ExecutorService executorService = Executors.newFixedThreadPool(clientcount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < clientcount; i++) {
            executorService.execute(() -> {
                String redisKey = RandomUtil.randomFromPool("AA_A", "BB_B", "CC_C", "DD_D", "EE_E");
                String lock = redisLock.lock(redisKey);
                count++;
                if (lock != null) {
                    System.out.println("获取到锁）" + redisKey + "）处理业务。线程" + Thread.currentThread().getId());
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisLock.unLock(redisKey, lock);
                } else {
                    System.out.println("未获取到锁（" + redisKey + "）处理业务。线程" + Thread.currentThread().getId());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        log.info("执行线程数:{},总耗时:{},count数为:{}", clientcount, end - start, count);
        return Result.success();
    }

    @RequestMapping(value = "idGenUtil", method = {RequestMethod.GET})
    @ApiOperation("ID 生成工具")
    public Result<?> idGenUtil(){
        String ck = idUtil.getDatePrefixId("CK", 6);
        return Result.success(ck);
    }

    @RequestMapping(value = "testConcurrentOpt", method = {RequestMethod.GET})
    @ApiOperation("并发操作")
    public Result<?> testConcurrentOpt(Long id, Long dealt){
        studentService.testConcurrentOpt(id, dealt);
        return Result.success();
    }
    @RequestMapping(value = "testConcurrentOptMinus", method = {RequestMethod.GET})
    @ApiOperation("并发操作")
    public Result<?> testConcurrentOptMinus(Long dealt){
        studentService.testConcurrentOptMinus(dealt);
        return Result.success();
    }
    @RequestMapping(value = "testProxy", method = {RequestMethod.GET})
    @ApiOperation("测试动态代理")
    public Result<?> testProxy(){
        return Result.success();
    }
    @RequestMapping(value = "testInsertSync", method = {RequestMethod.GET})
    @ApiOperation("测试testInsertSync")
    public Result<?> testInsertSync(){
        studentService.testInsertSync();
        return Result.success();
    }
    @RequestMapping(value = "testException", method = {RequestMethod.GET})
    @ApiOperation("测试testException")
    public Result<?> testException(String key){
        log.info("测试testException");
        if ("e".equals(key)) {
            int i = 1/0;
        }
        String ex = "java.lang.ArithmeticException: / by zero\n" +
                "\tat cn.acyou.scorpio.controller.sys.StudentController.testException(StudentController.java:290)\n" +
                "\tat cn.acyou.scorpio.controller.sys.StudentController$$FastClassBySpringCGLIB$$1d8a863.invoke(<generated>)\n" +
                "\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)";
        System.out.println(ex);
        Student student = new Student();
        student.setName(ex);
        student.setAge(22);
        student.setBirth(new Date());
        studentMapper.insertSelective(student);
        return Result.success(key);
    }

    @RequestMapping(value = "testResponseBodyMaxLength", method = {RequestMethod.GET})
    @ApiOperation("测试返回值的最大长度")
    public Result<?> testResponseBodyMaxLength(){
        List<Student> students = new ArrayList<>();
        /**
         * 50000  -> 436ms
         * 100000 -> 887ms
         */
        for (int i = 0; i < 100000; i++) {
            Student student = new Student();
            student.setName(RandomUtil.randomUserName());
            student.setAge(RandomUtil.randomAge());
            student.setId(RandomUtil.randomInt(6));
            student.setBirth(DateUtil.randomDate());
            students.add(student);
        }
        return Result.success(students);
    }
}
