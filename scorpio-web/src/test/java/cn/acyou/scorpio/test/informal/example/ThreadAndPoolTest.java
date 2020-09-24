package cn.acyou.scorpio.test.informal.example;

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
        //指定：corePoolSize = 0、maximumPoolSize = Integer.MAX_VALUE 的线程池
        //线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
        //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


        //指定：corePoolSize、maximumPoolSize 的线程池
        //(创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。)
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


        //指定：corePoolSize、maximumPoolSize 为1的线程池
        //**创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。**
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


        //可以延迟和定期执行的线程池     (创建一个定长线程池，支持定时及周期性任务执行。)
        ScheduledExecutorService  scheduledExecutor = Executors.newScheduledThreadPool(10);
        scheduledExecutor.schedule(() -> System.out.println("ok"), 1, TimeUnit.DAYS);
        //                                                                          初始延迟和周期执行
        scheduledExecutor.scheduleAtFixedRate(() -> System.out.println("ok"), 2, 3, TimeUnit.HOURS);
        //创建一个具有抢占式操作的线程池
        /*
         *  * 假设共有三个线程同时执行, A, B, C
         *  * 当A,B线程池尚未处理任务结束,而C已经处理完毕,则C线程会从A或者B中窃取任务执行,这就叫工作窃取
         *  * 假如A线程中的队列里面分配了5个任务，而B线程的队列中分配了1个任务，当B线程执行完任务后，它会主动的去A线程中窃取其他的任务进行执行
         *  * WorkStealingPool 背后是使用 ForkJoinPool实现的
         */
        //ForkJoinPool
        ExecutorService workStealingExecutor = Executors.newWorkStealingPool(10);
        ForkJoinPool forkJoinPool = new ForkJoinPool(10, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);

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
        //	at cn.acyou.scorpio.test.informal.example.ThreadAndPoolTest.main(ThreadAndPoolTest.java:40)
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
        //	at cn.acyou.scorpio.test.informal.example.ThreadAndPoolTest.main(ThreadAndPoolTest.java:40)
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

        //设置-Xmx10G后，在到524288000的时候Arrays.copyOf() 扩容的时候也发生了OOM
        //由于内存的的限制，似乎List的大小不会达到Integer.MAX_VALUE 21个亿
        //疑问：如果内存达到的时候会怎么样？
    }

    public static void grow(List<String> sourceList){
        sourceList.addAll(sourceList);
        System.out.println(sourceList.size());
        grow(sourceList);
    }


}
