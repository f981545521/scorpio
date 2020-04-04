package cn.acyou.scorpio.task.base;

import cn.acyou.framework.constant.Constant;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.scorpio.task.ScheduleJobLogService;
import cn.acyou.scorpio.task.entity.ScheduleJob;
import cn.acyou.scorpio.task.mapper.ScheduleJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 11:26]
 **/
@Slf4j
@Component
public class TaskParent implements ITask {

    private ScheduleJob scheduleJob;
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> future;


    public void taskBody(String params) {
        //实现
        log.info("执行了TaskParent，时间为:" + DateUtil.getCurrentDateFormat());
    }


    private Runnable doTask() {
        return () -> recordLogStart(true);
    }

    public void recordLogStart(boolean auto) {
        try {
            long start = System.currentTimeMillis();
            taskBody(scheduleJob.getParams());
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
    @Override
    public void run(ScheduleJob job) {
        scheduleJob = job;
        recordLogStart(false);
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
        job.setStatus(Constant.CONS_1);
        scheduleJobMapper.updateByPrimaryKeySelective(job);
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
        scheduleJob = job;
        future = threadPoolTaskScheduler.schedule(doTask(), new CronTrigger(job.getCronExpression()));
        log.info("resume job start : " + job.getBeanName());
    }
}
