package cn.acyou.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2019/11/20]
 **/
public class FastJsonUtil {

    /**
     * 使用JSON的方式，将map转换为bean
     *
     * @param map   map
     * @param clazz bean
     * @return clazz
     */
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        String jsonStr = JSON.toJSONString(map);
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 使用JSON的方式，将bean转换为map
     *
     * @param obj bean
     * @return MAP
     */
    public static <T> Map<String, String> beanToMap(Object obj) {
        String jsonStr = JSON.toJSONString(obj);
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 使用JSON的方式，将JSON字符串转换为MAP
     *
     * @param jsonStr JSON字符串
     * @return MAP
     */
    public static <T> Map<String, String> jsonStrToMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {
        });
    }

    public static <T> T jsonStrToBean(String jsonStr, Class<T> tClass) {
        return JSON.parseObject(jsonStr, tClass);
    }

    public static String toJsonString(Object bean) {
        return JSON.toJSONString(bean);
    }
}
