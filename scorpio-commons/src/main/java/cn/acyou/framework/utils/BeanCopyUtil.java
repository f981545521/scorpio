package cn.acyou.framework.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanCopyUtil {

    public static <T, E> E copy(T t, Class<E> clz) {
        if (t != null){
            try {
                E e = clz.newInstance();
                BeanUtils.copyProperties(t, e);
                return e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static <T, E> List<E> copyList(Collection<T> l, Class<E> clz) {
        List<E> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(l)) {
            l.forEach(item -> list.add(copy(item, clz)));
        }
        return list;
    }

    public static <M> void merge(M target, M destination) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod().invoke(target);
                if (originalValue == null) {
                    Object defaultValue = descriptor.getReadMethod().invoke(destination);
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }
            }
        }
    }

}
