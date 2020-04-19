package cn.acyou.scorpio.mapper.task;

import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.framework.utils.SpringHelper;
import cn.acyou.scorpio.mapper.task.base.ITask;
import cn.acyou.scorpio.mapper.task.entity.ScheduleJob;
import cn.acyou.scorpio.mapper.task.mapper.ScheduleJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author yofuang
 * @version [1.0.0, 2020-4-4 下午 10:05]
 **/
@Service
public class ScheduleJobService extends ServiceImpl<ScheduleJobMapper, ScheduleJob> {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        Example condition = new Example(ScheduleJob.class);
        condition.createCriteria().andEqualTo("status", 0);
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectByExample(condition);
        for (ScheduleJob scheduleJob : scheduleJobs) {
            ITask iTask = SpringHelper.getBean(scheduleJob.getBeanName());
            iTask.resumeJob(scheduleJob);
        }
    }

    public void run(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.runJob(scheduleJob);
    }

    public void pause(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.pauseJob(scheduleJob);
    }

    public void resume(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.resumeJob(scheduleJob);
    }
}
