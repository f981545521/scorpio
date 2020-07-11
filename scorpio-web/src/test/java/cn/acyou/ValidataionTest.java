package cn.acyou;

import javax.validation.constraints.NotNull;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-11 下午 02:31]
 **/
public class ValidataionTest {

    public static void main(String[] args) {
        String string = getString(null);
        System.out.println("正常结束了：" + string);
    }

    public static String getString(@NotNull Stu stu){
        System.out.println("进入了");
        return stu.getName();
    }


}

class Stu{
    @NotNull
    private String name;

    public Stu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
