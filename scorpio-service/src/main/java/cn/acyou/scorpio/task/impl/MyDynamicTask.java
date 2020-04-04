package cn.acyou.scorpio.task.impl;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.scorpio.task.ITask;
import cn.acyou.scorpio.task.entity.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 09:20]
 **/
@Slf4j
@Component
public class MyDynamicTask implements ITask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    private Runnable doTask() {
        return this::taskBody;
    }

    public void taskBody() {
        log.info("执行了MyDynamicTask，时间为:" + DateUtil.getCurrentDateFormat());
    }


    /**
     * 执行定时任务接口
     *
     * @param job 任务
     */
    @Override
    public void run(ScheduleJob job) {
        taskBody();
        log.info("run job method : " + job.getBeanName());
    }

    /**
     * 停止定时任务接口
     *
     * @param job 任务
     */
    @Override
    public void pause(ScheduleJob job) {
        if (future != null) {
            future.cancel(true);
        }
        log.info("stop job : " + job.getBeanName());
    }

    /**
     * 恢复定时任务接口
     *
     * @param job 任务
     */
    @Override
    public void resume(ScheduleJob job) {
        pause(job);
        future = threadPoolTaskScheduler.schedule(doTask(), new CronTrigger(job.getCronExpression()));
        log.info("resume job start : " + job.getBeanName());
    }
}
