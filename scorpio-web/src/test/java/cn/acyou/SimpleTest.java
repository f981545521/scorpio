package cn.acyou;

import org.junit.Test;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-5 下午 12:09]
 **/
public class SimpleTest {

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
    public void test4(){
        Long a = -1L;
        System.out.println(-2 > a);
        System.out.println(-1 > a);
        System.out.println(0 > a);
        System.out.println(1 > a);
    }

    private static String outName(String name){
        //assert name != null;//java.lang.AssertionError
        Assert.notNull(name, "name 不能为空！");//cn.acyou.framework.exception.AssertException: name 不能为空！
        System.out.println(name);
        return name;
    }
}
