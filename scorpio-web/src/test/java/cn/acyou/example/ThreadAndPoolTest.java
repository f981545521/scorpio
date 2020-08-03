package cn.acyou.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author youfang
 * @version [1.0.0, 2020-8-2 下午 07:16]
 **/
public class ThreadAndPoolTest {

    @Test
    public void test1(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);
        ExecutorService workStealingPool = Executors.newWorkStealingPool(10);

    }

    @Test
    public void testThreadPool(){
        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        //队列大小默认是: Integer.MAX_VALUE
        System.out.println(Integer.MAX_VALUE);


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    @Test
    public void testLinkedBlockingDeque(){
        //阻塞队列 add满了后会抛异常
        LinkedBlockingDeque<String> objects = new LinkedBlockingDeque<>(10);

        for (int i = 0; i < 1000; i++) {
            objects.add("ok" + i);
        }
        //take
        //put
        System.out.println("ok");
        //Exception in thread "main" java.lang.IllegalStateException: Deque full
        //	at java.util.concurrent.LinkedBlockingDeque.addLast(LinkedBlockingDeque.java:335)
        //	at java.util.concurrent.LinkedBlockingDeque.add(LinkedBlockingDeque.java:633)
        //	at cn.acyou.example.ThreadAndPoolTest.main(ThreadAndPoolTest.java:40)
    }

    @Test
    public void testDelayDeque(){
        //阻塞队列 add满了后会抛异常
        LinkedBlockingDeque<String> objects = new LinkedBlockingDeque<>(10);

        for (int i = 0; i < 1000; i++) {
            objects.add("ok" + i);
        }
        //take
        //put
        System.out.println("ok");
        //Exception in thread "main" java.lang.IllegalStateException: Deque full
        //	at java.util.concurrent.LinkedBlockingDeque.addLast(LinkedBlockingDeque.java:335)
        //	at java.util.concurrent.LinkedBlockingDeque.add(LinkedBlockingDeque.java:633)
        //	at cn.acyou.example.ThreadAndPoolTest.main(ThreadAndPoolTest.java:40)
    }

    @Test
    public void testListMaxSize(){
        //疑问：List最大长度是多大，TODO : 去公司执行一下试试。
        List<String> objects = new ArrayList<>(10);
        long i = 0;
        while (i < Long.MAX_VALUE) {
            objects.add("ok" + i++);
        }
        System.out.println("ok");
    }

    @Test
    public void test2(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10));
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行中...");
                    System.out.println(threadPoolExecutor.getActiveCount() + ",任务数量：" + threadPoolExecutor.getTaskCount() + "，完成任务数量：" + threadPoolExecutor.getCompletedTaskCount());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("执行结束。");
                }
            });
        }

    }

    public static void main(String[] args) {
        //疑问：List最大长度是多大
        List<String> sourceList = new ArrayList<>();

        //sourceList.add(0, "c");
        //sourceList.add(111, "c");


        List<String> objects = new ArrayList<>();
        //Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit

        for (int i = 0; i < 1000; i++) {
            sourceList.add("1");
        }




        grow(sourceList);
        //262144000
        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    }

    public static void grow(List<String> sourceList){
        sourceList.addAll(sourceList);
        System.out.println(sourceList.size());
        grow(sourceList);
    }


}
