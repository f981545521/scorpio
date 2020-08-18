package cn.acyou;

import cn.acyou.scorpio.system.entity.Student;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-5 下午 12:09]
 **/
public class SimpleTest {

    private static final Logger log = LoggerFactory.getLogger(SimpleTest.class);

    public static void main(String[] args) {
        //String shortClassName = ClassUtils.getShortName(TestTask.class);
        //System.out.println(Introspector.decapitalize(shortClassName));

        //ParamConfig paramConfig = new ParamConfig();
        //paramConfig.setCreateUser(123L);
        //paramConfig.setCreateTime(new Date());
        //paramConfig.setName("好的");
        //System.out.println(paramConfig);
        //System.out.println(paramConfig.getCreateUser());
        //System.out.println(paramConfig.getCreateTime());

        testNotNullAnnotation(null);
    }

    @Test
    public void test1(){
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println(UUID.randomUUID().toString().replace("-", "").length());
        System.out.println("8b37fc19c27011ea8c580242ac110002".length());
    }

    public static void testNotNullAnnotation(@NotNull Long userId){
        System.out.println(userId);
    }

    @Test
    public void test3(){
        System.out.println("come on");
        outName(null);
    }

    @Test
    public void test88(){
        log.info("hello, i'm {}, age {}。", "james", 10);
        String format = MessageFormat.format("加工失败。本次可加工数量为{0}，此次加工数量为{1}。", 100, 200);
        System.out.println(format);
        System.out.println(String.format("加工失败。本次可加工数量为%s，此次加工数量为%s。", 100, 200));
        String[] argumentArray = Arrays.array("100", "200");
        System.out.println(MessageFormatter.arrayFormat("加工失败。本次可加工数量为{}，此次加工数量为{}。", argumentArray).getMessage());
        //System.out.println(ExtendedMessageFormat.format("加工失败。本次可加工数量为{}，此次加工数量为{}。", argumentArray));



    }

    @Test
    public void test4(){
        Long a = -1L;
        System.out.println(-2 > a);
        System.out.println(-1 > a);
        System.out.println(0 > a);
        System.out.println(1 > a);
    }

    @Test
    public void test4112(){
        Long a = 110L;
        assert a == 110;
        assert a == 111;
        System.out.println("ok");
    }
    @Test
    public void testJSON1(){
        List<String> jsonStrList = new ArrayList<>();
        jsonStrList.add("{\"age\":22,\"id\":20,\"name\":\"小王\"}");
        jsonStrList.add("{\"age\":23,\"id\":20,\"name\":\"小王\"}");
        jsonStrList.add("{\"age\":24,\"id\":20,\"name\":\"小王\"}");
        jsonStrList.add("{\"age\":25,\"id\":20,\"name\":\"小王\"}");
        String s = JSON.toJSONString(jsonStrList);
        //[{"age":22,"id":22,"name":"信息"},{"age":23,"id":23,"name":"信息3"}]
        System.out.println(s);
        JSONArray objects = JSON.parseArray(s);

        List<Student> students1 = JSON.parseArray(s, Student.class);
        System.out.println(students1);
        List<Student> students = JSON.parseObject(s, new TypeReference<List<Student>>(){});
        System.out.println(students);
    }
    @Test
    public void testJSON2(){
        List<Student> jsonStrList = new ArrayList<>();
        Student student = new Student();
        student.setId(22);
        student.setName("信息");
        student.setAge(22);
        jsonStrList.add(student);
        Student student2 = new Student();
        student2.setId(23);
        student2.setName("信息3");
        student2.setAge(23);
        jsonStrList.add(student2);



        String s = JSON.toJSONString(jsonStrList);
        System.out.println(s);
        JSONArray objects = JSON.parseArray(s);

        List<Student> students1 = JSON.parseArray(s, Student.class);
        System.out.println(students1);
        List<Student> students = JSON.parseObject(s, new TypeReference<List<Student>>(){});
        System.out.println(students);
    }

    private static String outName(String name){
        //assert name != null;//java.lang.AssertionError
        Assert.notNull(name, "name 不能为空！");//cn.acyou.framework.exception.AssertException: name 不能为空！
        System.out.println(name);
        return name;
    }
}
