package cn.acyou.jol;

import cn.acyou.test.Animal;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author youfang
 * @version [1.0.0, 2020-8-9 上午 11:06]
 **/
@Slf4j
public class JolTest {
    static Animal person = new Animal();
    public static void main(String[] args) {
        log.info(ClassLayout.parseInstance(person).toPrintable());
    }
}
