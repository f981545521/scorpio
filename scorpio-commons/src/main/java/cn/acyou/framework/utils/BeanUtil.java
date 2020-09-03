package cn.acyou.framework.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class BeanUtil {

    /**
     * 将Object 转换为Map key-属性名 value-属性值
     * @param obj bean
     * @return Map key-属性名 value-属性值
     */
    public static HashMap<String, Object> convertToMap(Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            if (varName.equals("serialVersionUID")) {
                continue;
            }
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            Object o = null;
            try {
                o = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o != null)
                map.put(varName, o.toString());
            field.setAccessible(accessFlag);
        }
        return map;
    }

}
