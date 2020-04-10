package cn.acyou.framework.utils.amap;

import cn.acyou.framework.utils.HttpClientUtil;
import cn.acyou.framework.utils.IdCardUtil;
import cn.acyou.framework.utils.amap.entity.AmapAddress;
import com.alibaba.fastjson.JSON;

/**
 *
 * 高德地图：
 *      示例中心：https://lbs.amap.com/api/javascript-api/example/map-lifecycle/map-show
 *
 * 腾讯地图：
 *      示例中心：https://lbs.qq.com/webApi/javascriptV2/jsDemo
 * @author youfang
 * @version [1.0.0, 2020/4/8]
 **/
public class AmapUtil {

    public static AmapAddress address(String lng, String lat){
        String locationStr = lng + "," + lat;
        String url = "https://restapi.amap.com/v3/geocode/regeo?key=0fa2149a2c4a136e236a4cd7fe086e2d&location=" + locationStr;
        System.out.println(url);
        String result = HttpClientUtil.doGet(url);
        return JSON.parseObject(result, AmapAddress.class);
    }

    public static ProvinceCityArea getProvinceCityArea(String lng, String lat){
        AmapAddress address = address(lng, lat);
        String adcode = address.getRegeocode().getAddressComponent().getAdcode();
        String adname = IdCardUtil.AREA_CODE.get(Integer.valueOf(adcode));
        if (adname != null){
            ProvinceCityArea area = new ProvinceCityArea();
            if (adcode.endsWith("0000")){
                area.proviceId = adcode;
                area.proviceName = adname;
            }else if (adcode.endsWith("00")){
                area.cityId = adcode;
                area.cityName = adname;
                String sProvinceNum = adcode.substring(0, 2) + "0000";
                String sProvinceName = IdCardUtil.AREA_CODE.get(Integer.parseInt(sProvinceNum));
                area.proviceId = sProvinceNum;
                area.proviceName = sProvinceName;
            }else {
                area.areaId = adcode;
                area.areaName = adname;
                String sCityNum = adcode.substring(0, 4) + "00";
                String sCityName = IdCardUtil.AREA_CODE.get(Integer.parseInt(sCityNum));
                area.cityId = sCityNum;
                area.cityName = sCityName;
                String sProvinceNum = adcode.substring(0, 2) + "0000";
                String sProvinceName = IdCardUtil.AREA_CODE.get(Integer.parseInt(sProvinceNum));
                area.proviceId = sProvinceNum;
                area.proviceName = sProvinceName;
            }
            return area;
        }else {
            return null;
        }

    }

    public static void main(String[] args) {
        ProvinceCityArea address = getProvinceCityArea("119.83382", "31.938324");
        System.out.println(address);
    }



}

class ProvinceCityArea{
    public String proviceId;
    public String proviceName;
    public String cityId;
    public String cityName;
    public String areaId;
    public String areaName;

    public ProvinceCityArea() {
    }

    @Override
    public String toString() {
        return "ProvinceCityArea{" +
                "proviceName='" + proviceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
