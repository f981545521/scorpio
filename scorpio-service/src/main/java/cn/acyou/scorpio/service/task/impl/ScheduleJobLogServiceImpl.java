package cn.acyou.scorpio.service.task.impl;

import cn.acyou.framework.constant.Constant;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.scorpio.tool.entity.ScheduleJob;
import cn.acyou.scorpio.tool.entity.ScheduleJobLog;
import cn.acyou.scorpio.tool.mapper.ScheduleJobLogMapper;
import cn.acyou.scorpio.service.task.ScheduleJobLogService;
import cn.acyou.scorpio.service.task.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 10:59]
 **/
@Service
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {
    @Autowired
    private ScheduleJobLogMapper scheduleJobLogMapper;
    @Autowired
    private ScheduleJobService scheduleJobService;


    /**
     * 成功
     *
     * @param scheduleJob 任务
     * @param remark      备注
     * @param times       耗时
     */
    @Override
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

        ScheduleJob updateScheduleJob = new ScheduleJob();
        updateScheduleJob.setJobId(scheduleJob.getJobId());
        updateScheduleJob.setLastExecuteTime(new Date());
        scheduleJobService.updateByPrimaryKeySelective(updateScheduleJob);
    }

    /**
     * 错误
     *
     * @param scheduleJob  任务
     * @param errorMessage 错误消息
     */
    @Override
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
