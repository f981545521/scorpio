package cn.acyou;

import cn.acyou.framework.model.BaseEntity;
import cn.acyou.scorpio.schedules.TestTask;
import cn.acyou.scorpio.tool.entity.ParamConfig;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
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
}
