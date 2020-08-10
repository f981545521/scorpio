package cn.acyou.testlocks;

/**
 * @author youfang
 * @version [1.0.0, 2020-8-10 下午 10:10]
 **/
public class SynchronizedTest {
    public static void main(String[] args) {
        test();
    }

    public static synchronized void test() {
        System.out.println("Hello Word");
    }
}
