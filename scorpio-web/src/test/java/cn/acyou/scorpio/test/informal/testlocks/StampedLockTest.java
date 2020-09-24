package cn.acyou.scorpio.test.informal.testlocks;

import java.util.concurrent.locks.StampedLock;

/**
 * @author youfang
 * @version [1.0.0, 2020/8/11]
 **/
public class StampedLockTest {
    private static final StampedLock lock = new StampedLock();

    public static void main(String[] args) {
        long l2 = lock.writeLock();
        System.out.println(l2);
        long l = lock.readLock();
        long l1 = lock.tryReadLock();

        System.out.println(l);
        System.out.println(l1);
        lock.unlock(l);
        System.out.println("ok");
    }



    private double balance;
    public void deposit(double amount) {
        long stamp = lock.writeLock();
        try {
            balance = balance + amount;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    public double getBalance() {
        long stamp = lock.readLock();
        try {
            return balance;
        } finally {
            lock.unlockRead(stamp);
        }
    }

}
