package cn.acyou;

import cn.acyou.scorpio.service.task.customize.TestTask;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-5 下午 12:09]
 **/
public class SimpleTest {

    public static void main(String[] args) {
        String shortClassName = ClassUtils.getShortName(TestTask.class);
        System.out.println(Introspector.decapitalize(shortClassName));
    }
}
