package cn.acyou.scorpio.task;

import cn.acyou.framework.constant.Constant;
import cn.acyou.scorpio.task.entity.ScheduleJob;
import cn.acyou.scorpio.task.entity.ScheduleJobLog;
import cn.acyou.scorpio.task.mapper.ScheduleJobLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 10:59]
 **/
@Service
public class ScheduleJobLogService {
    @Autowired
    private ScheduleJobLogMapper scheduleJobLogMapper;


    /**
     * 成功
     *
     * @param scheduleJob 任务
     * @param remark      备注
     * @param times       耗时
     */
    public void success(ScheduleJob scheduleJob, String remark, Integer times) {
        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setCreateTime(new Date());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setStatus(Constant.CONS_0);
        jobLog.setTimes(times);
        jobLog.setRemark(remark);
        scheduleJobLogMapper.insertSelective(jobLog);
    }

    /**
     * 错误
     *
     * @param scheduleJob  任务
     * @param errorMessage 错误消息
     */
    public void error(ScheduleJob scheduleJob, String errorMessage) {
        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setCreateTime(new Date());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setStatus(Constant.CONS_1);
        jobLog.setError(errorMessage);
        scheduleJobLogMapper.insertSelective(jobLog);
    }
}
