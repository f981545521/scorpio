package cn.acyou.scorpio.schedules.base;

import cn.acyou.framework.constant.Constant;
import cn.acyou.scorpio.tool.entity.ScheduleJob;
import cn.acyou.scorpio.tool.mapper.ScheduleJobMapper;
import cn.acyou.scorpio.service.task.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

/**
 *
 * 定时任务父类
 *
 * 因为子类要实例化前，一定会先实例化他的父类。这样创建了继承抽象类的子类的对象，也就具有其父类（抽象类）所有属性和方法了
 *
 * 继承此类，重写抽象方法{@link #run(String)}
 *
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 11:26]
 **/
@Slf4j
public abstract class TaskParent {

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduleJob scheduleJob;
    private ScheduledFuture<?> future;


    /**
     * 抽象方法 ： 子类必须重写
     * @param params 参数
     */
    public abstract void run(String params);


    private Runnable doTask() {
        return () -> recordLogStart(true);
    }

    public void recordLogStart(boolean auto) {
        try {
            long start = System.currentTimeMillis();
            run(scheduleJob.getParams());
            long times = System.currentTimeMillis() - start;
            if (auto) {
                scheduleJobLogService.success(scheduleJob, "自动执行成功", Long.valueOf(times).intValue());
            } else {
                scheduleJobLogService.success(scheduleJob, "手动执行成功", Long.valueOf(times).intValue());
            }
        } catch (Exception e) {
            scheduleJobLogService.error(scheduleJob, e.getMessage());
        }
    }


    /**
     * 执行定时任务接口
     *
     * @param job 任务
     */
    public void runJob(ScheduleJob job) {
        scheduleJob = job;
        recordLogStart(false);
        log.info("run job method : " + job.getBeanName());
    }

    /**
     * 停止定时任务接口
     *
     * @param job 任务
     */
    public void pauseJob(ScheduleJob job) {
        if (future != null) {
            future.cancel(true);
        }
        job.setStatus(Constant.CONS_1);
        scheduleJobMapper.updateByPrimaryKeySelective(job);
        log.info("stop job : " + job.getBeanName());
    }

    /**
     * 恢复定时任务接口
     *
     * @param job 任务
     */
    public void resumeJob(ScheduleJob job) {
        if (future != null) {
            future.cancel(true);
        }
        scheduleJob = job;
        future = threadPoolTaskScheduler.schedule(doTask(), new CronTrigger(job.getCronExpression()));
        log.info("resume job start : " + job.getBeanName());
    }
}
