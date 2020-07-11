package cn.acyou;


import cn.acyou.framework.utils.Assert;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-11 下午 02:31]
 **/
public class ValidataionTest {

    public static void main(String[] args) {
        String string = getString(null);
        System.out.println("正常结束了：" + string);
    }

    public static String getString(String name){
        Assert.notNull(name, "name不呢为空！");
        return name;
    }


}
