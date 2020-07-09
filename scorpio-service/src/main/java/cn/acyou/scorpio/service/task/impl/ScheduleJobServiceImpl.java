package cn.acyou.scorpio.service.task.impl;

import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.framework.utils.SpringHelper;
import cn.acyou.scorpio.tool.entity.ScheduleJob;
import cn.acyou.scorpio.tool.mapper.ScheduleJobMapper;
import cn.acyou.scorpio.schedules.base.TaskParent;
import cn.acyou.scorpio.service.task.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yofuang
 * @version [1.0.0, 2020-4-4 下午 10:05]
 **/
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public void run(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        TaskParent iTask = SpringHelper.getBean(beanName);
        iTask.runJob(scheduleJob);
    }
    @Override
    public void pause(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        TaskParent iTask = SpringHelper.getBean(beanName);
        iTask.pauseJob(scheduleJob);
    }
    @Override
    public void resume(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        TaskParent iTask = SpringHelper.getBean(beanName);
        iTask.resumeJob(scheduleJob);
    }
}
