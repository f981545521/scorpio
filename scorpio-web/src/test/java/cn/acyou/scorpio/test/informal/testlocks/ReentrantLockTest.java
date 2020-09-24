package cn.acyou.scorpio.test.informal.testlocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁实现
 *
 * 对于公平锁的实现，就要结合着我们的可重入性质了。公平锁的含义我们上面已经说了，就是谁等的时间最长，谁就先获取锁。
 * @author youfang
 * @version [1.0.0, 2020-8-10 下午 09:52]
 **/
public class ReentrantLockTest {
    //new一个ReentrantLock的时候参数为true，表明实现公平锁机制。new ReentrantLock(true);
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(ReentrantLockTest::test, "Thread - A").start();
        new Thread(ReentrantLockTest::test, "Thread - B").start();
        new Thread(ReentrantLockTest::test, "Thread - C").start();
        new Thread(ReentrantLockTest::test, "Thread - D").start();
        new Thread(ReentrantLockTest::test, "Thread - E").start();
    }

    public static void test(){
        for (int i = 0; i < 2; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                TimeUnit.SECONDS.sleep(2);
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
