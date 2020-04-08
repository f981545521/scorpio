package cn.acyou.framework.utils;

/**
 * @author youfang
 * @version [1.0.0, 2020/4/8]
 **/
public class AmapUtil {

    public static String address(String lng, String lat){
        String locationStr = lng + "," + lat;
        String url = "https://restapi.amap.com/v3/geocode/regeo?key=0fa2149a2c4a136e236a4cd7fe086e2d&location=" + locationStr;
        System.out.println(url);
        String result = HttpClientUtil.doGet(url);
        System.out.println(result);
        return "";
    }

    public static void main(String[] args) {
        String address = address("118.83382", "31.938324");
        System.out.println(address);
    }
}
