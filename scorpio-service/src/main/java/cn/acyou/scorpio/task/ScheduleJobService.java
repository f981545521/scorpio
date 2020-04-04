package cn.acyou.scorpio.task;

import cn.acyou.framework.utils.SpringHelper;
import cn.acyou.scorpio.task.base.ITask;
import cn.acyou.scorpio.task.entity.ScheduleJob;
import cn.acyou.scorpio.task.mapper.ScheduleJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 10:05]
 **/
@Service
public class ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        Condition condition = new Condition(ScheduleJob.class);
        condition.createCriteria().andEqualTo("status", 0);
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectByCondition(condition);
        for (ScheduleJob scheduleJob : scheduleJobs) {
            ITask iTask = SpringHelper.getBean(scheduleJob.getBeanName());
            iTask.resume(scheduleJob);
        }
    }

    public void run(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.run(scheduleJob);
    }

    public void pause(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.pause(scheduleJob);
    }

    public void resume(Long jobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
        String beanName = scheduleJob.getBeanName();
        ITask iTask = SpringHelper.getBean(beanName);
        iTask.resume(scheduleJob);
    }
}
