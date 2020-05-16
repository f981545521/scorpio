package cn.acyou.framework.utils;

import cn.acyou.framework.model.StatusEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-5-16 下午 02:08]
 **/
public class AppStatusUtil {

    public static List<StatusEntity> listAllStatus(Class clazz) {
        List<StatusEntity> resultList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(clazz);
                if (o instanceof StatusEntity) {
                    resultList.add((StatusEntity) o);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static String getStatusName(Class clazz, Integer code){
        List<StatusEntity> statusEntities = listAllStatus(clazz);
        for (StatusEntity statusEntity : statusEntities){
            if (code.equals(statusEntity.getCode())){
                return statusEntity.getName();
            }
        }
        return "";
    }
}
