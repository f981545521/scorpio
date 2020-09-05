package cn.acyou;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-5 上午 08:54]
 **/
public class ThreadDaemonTest {

    private static int counter = 1;

    public static void main(String[] args) {

        System.out.println("主线程 start");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counter < 10){
                    counter ++;
                    try {
                        System.out.println("线程T1 在运行.." + counter);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.setDaemon(true);
        t1.start();

        System.out.println("主线程 结束");

        //如果是非damon线程，会标记为用户线程。直到运行完成后JVM才推出
        //如果是damon线程，主线程完成后，直接推出
    }
}
