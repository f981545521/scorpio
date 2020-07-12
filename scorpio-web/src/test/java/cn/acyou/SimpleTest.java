package cn.acyou;

import cn.acyou.framework.utils.Assert;
import cn.acyou.scorpio.tool.entity.ParamConfig;
import org.junit.Test;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-5 下午 12:09]
 **/
public class SimpleTest {

    public static void main(String[] args) {
        //String shortClassName = ClassUtils.getShortName(TestTask.class);
        //System.out.println(Introspector.decapitalize(shortClassName));

        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setCreateUser(123L);
        paramConfig.setCreateTime(new Date());
        paramConfig.setName("好的");
        System.out.println(paramConfig);

        System.out.println(paramConfig.getCreateUser());
        System.out.println(paramConfig.getCreateTime());

    }

    @Test
    public void test1(){
        System.out.println("come on");
        outName(null);
    }

    private static String outName(String name){
        //assert name != null;//java.lang.AssertionError
        Assert.notNull(name, "name 不能为空！");//cn.acyou.framework.exception.AssertException: name 不能为空！
        System.out.println(name);
        return name;
    }
}
