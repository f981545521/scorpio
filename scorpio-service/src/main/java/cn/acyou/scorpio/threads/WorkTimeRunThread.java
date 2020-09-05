package cn.acyou.scorpio.threads;

import cn.acyou.framework.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 工作时间运行线程
 * @author youfang
 * @version [1.0.0, 2020-9-4 下午 10:38]
 **/
public class WorkTimeRunThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(WorkTimeRunThread.class);
    private static final String THREAD_NAME = "WorkTimeRunThread";

    public WorkTimeRunThread() {
        super(THREAD_NAME);
    }

    @Override
    public void run() {
        /**
         * 参考：https://help.aliyun.com/document_detail/141779.html?spm=a2c4g.11186623.6.631.f3b75627NytP7A
         * RocketMQ 客户端中，基本上都是通过while (true)来实现的
         * <pre>
         *        static void Main(string[] args){
         *             // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息
         *             while (true){
         *                  。。。。
         *             }
         *        }
         * </pre>
         *
         * 参考：{@link Timer.mainLoop}
         *
         * 6个延时队列的实现方案: http://www.dockone.io/article/10139
         */
        while (true) {
            if (new Date().before(DateUtil.parseSpecificDateTime("2020-09-04 23:20:00"))) {
                log.info("WorkTimeRunThread running ok .");
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(60 * 60 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
