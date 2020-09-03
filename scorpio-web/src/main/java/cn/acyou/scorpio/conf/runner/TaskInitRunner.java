package cn.acyou.scorpio.conf.runner;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.SpringHelper;
import cn.acyou.scorpio.schedules.base.TaskParent;
import cn.acyou.scorpio.service.task.ScheduleJobService;
import cn.acyou.scorpio.tool.entity.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用`@PostConstruct`注解实现的时候，发现未完全注入问题，List中第一个元素需要注入的属性都为null！！
 * 使用ApplicationRunner来解决此问题
 *
 * @author youfang
 * @version [1.0.0, 2020/7/1]
 **/
@Slf4j
@Component
public class TaskInitRunner implements ApplicationRunner {
    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private List<TaskParent> taskParentList;

    @Override
    public void run(ApplicationArguments args) {
        //定时任务
        Example condition = new Example(ScheduleJob.class);
        condition.createCriteria().andEqualTo("status", 0);
        List<ScheduleJob> scheduleJobs = scheduleJobService.selectByExample(condition);
        for (ScheduleJob scheduleJob : scheduleJobs) {
            if (SpringHelper.containsBean(scheduleJob.getBeanName())){
                TaskParent iTask = SpringHelper.getBean(scheduleJob.getBeanName());
                iTask.resumeJob(scheduleJob);
            }else {
                log.error("定时器 {} 不存在，请检查", scheduleJob.getBeanName());
            }
        }


        //启动线程监听
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                timer.cancel();
            }
        }, DateUtil.parseSpecificDateTime("2020-09-03 12:12:12"), 10);
    }
}
