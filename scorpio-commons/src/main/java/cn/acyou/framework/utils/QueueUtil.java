package cn.acyou.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author youfang
 * @version [1.0.0, 2019/11/28]
 **/
public class QueueUtil {

    public static <E> List<E> dequePop(Queue<E> objDeque, int count) {
        List<E> dequeList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            E e = objDeque.poll();
            if (e != null) {
                dequeList.add(e);
            }
        }
        return dequeList;
    }


}
