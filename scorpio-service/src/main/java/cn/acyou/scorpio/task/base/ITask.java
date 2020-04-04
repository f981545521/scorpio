package cn.acyou.scorpio.task.base;

import cn.acyou.scorpio.task.entity.ScheduleJob;

/**
 * 定时任务接口，所有定时任务都要实现该接口
 *
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 10:01]
 **/
public interface ITask {
    /**
     * 运行
     * 执行定时任务接口
     *
     * @param job 工作
     */
    void runJob(ScheduleJob job);

    /**
     * 暂停
     * 停止定时任务接口
     *
     * @param job 工作
     */
    void pauseJob(ScheduleJob job);

    /**
     * 恢复
     * 恢复定时任务接口
     *
     * @param job 工作
     */
    void resumeJob(ScheduleJob job);
}
