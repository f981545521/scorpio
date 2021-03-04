package cn.acyou.scorpio.test.informal.dll;

/**
 * @author youfang
 * @version [1.0.0, 2021/3/4]
 **/
public class JnaTest {
    public static void main(String[] args) {
        int i = CLibrary.INSTANCE.ZM_GetInfo();
        System.out.println(i);
    }
}
