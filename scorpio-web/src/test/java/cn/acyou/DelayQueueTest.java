package cn.acyou;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-5 下午 12:57]
 **/
public class DelayQueueTest {
    public static void main(String[] args) throws Exception{
        DelayTask Order1 = new DelayTask("Order1", 5, TimeUnit.SECONDS);
        DelayTask Order2 = new DelayTask("Order2", 10, TimeUnit.SECONDS);
        DelayTask Order3 = new DelayTask("Order3", 15, TimeUnit.SECONDS);
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        delayQueue.put(Order1);
        delayQueue.put(Order2);
        delayQueue.put(Order3);

        System.out.println("订单延迟队列开始时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        while (delayQueue.size() != 0) {
            /*
             * 取队列头部元素是否过期
             */
            DelayTask task = delayQueue.poll();
            if (task != null) {
                System.out.format("订单:{%s}被取消, 取消时间:{%s}\n", task.name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            Thread.sleep(1000);
        }
    }

    static class DelayTask implements Delayed {
        /**
         * 延迟时间
         */
        @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private long time;
        String name;

        public DelayTask(String name, long time, TimeUnit unit) {
            this.name = name;
            this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
        }
        @Override
        public long getDelay(TimeUnit unit) {
            return time - System.currentTimeMillis();
        }
        @Override
        public int compareTo(Delayed o) {
            DelayTask Order = (DelayTask) o;
            long diff = this.time - Order.time;
            if (diff <= 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
