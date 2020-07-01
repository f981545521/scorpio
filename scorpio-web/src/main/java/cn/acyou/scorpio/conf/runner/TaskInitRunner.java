package cn.acyou.scorpio.conf.runner;

import cn.acyou.framework.utils.SpringHelper;
import cn.acyou.scorpio.mapper.task.entity.ScheduleJob;
import cn.acyou.scorpio.schedules.base.ITask;
import cn.acyou.scorpio.service.task.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 使用`@PostConstruct`注解实现的时候，发现未完全注入问题，List中第一个元素需要注入的属性都为null！！
 * 使用ApplicationRunner来解决此问题
 * @author youfang
 * @version [1.0.0, 2020/7/1]
 **/
@Slf4j
@Component
public class TaskInitRunner implements ApplicationRunner {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Example condition = new Example(ScheduleJob.class);
        condition.createCriteria().andEqualTo("status", 0);
        List<ScheduleJob> scheduleJobs = scheduleJobService.selectByExample(condition);
        for (ScheduleJob scheduleJob : scheduleJobs) {
            ITask iTask = SpringHelper.getBean(scheduleJob.getBeanName());
            iTask.resumeJob(scheduleJob);
        }
    }
}
