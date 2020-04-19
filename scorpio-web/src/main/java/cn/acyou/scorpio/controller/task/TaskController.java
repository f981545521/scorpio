package cn.acyou.scorpio.controller.task;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.mapper.task.TaskVo;
import cn.acyou.scorpio.service.task.ScheduleJobService;
import cn.acyou.scorpio.service.task.base.ITask;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 09:30]
 **/
@RestController
@Api(tags = "动态定时任务", description = "SpringBoot Schedule 实现定时任务")
public class TaskController {

    @Autowired
    private List<ITask> iTaskList = Lists.newArrayList();

    @Autowired
    private ScheduleJobService scheduleJobService;

    @GetMapping("/tasks")
    @ApiOperation(value = "获取所有定时器")
    public Result<List<TaskVo>> tasks() {
        List<TaskVo> taskVoList = new ArrayList<>();
        for (ITask task: iTaskList){
            TaskVo taskVo = new TaskVo();
            taskVo.setName(task.getClass().getName());
            /*
             * why use Introspector.decapitalize ?
             *
             * 1. 包扫描
             * {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan(String...)}
             * 2. 扫描获取beanName
             *  <pre>
             *      String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
             *  </pre>
             *  3. 生成bean名称 {@link org.springframework.context.annotation.AnnotationBeanNameGenerator#generateBeanName(BeanDefinition, BeanDefinitionRegistry)}
             *                 {@link org.springframework.context.annotation.AnnotationBeanNameGenerator#buildDefaultBeanName(BeanDefinition)} (BeanDefinition, BeanDefinitionRegistry)}
             *                 最后由方法：{@link Introspector#decapitalize(java.lang.String)} 生成bean的名称
             *     为什么这么做？
             *     Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays as "URL".
             */
            taskVo.setSimpleName(Introspector.decapitalize(task.getClass().getSimpleName()));
            taskVoList.add(taskVo);
        }
        return Result.success(taskVoList);
    }
    @GetMapping("/run")
    @ApiOperation(value = "立即执行任务", notes = "根据jobIds执行任务（显示在内部的详细信息）")
    public Result<String> run(Long jobIds) {
        scheduleJobService.run(jobIds);
        return Result.success();
    }

    @ApiOperation(value = "暂停定时任务")
    @GetMapping("/pause")
    public Result<String> pause(Long jobIds) {
        scheduleJobService.pause(jobIds);
        return Result.success();
    }

    @GetMapping("/resume")
    @ApiOperation(value = "恢复定时任务")
    public Result<String> resume(Long jobIds) {
        scheduleJobService.resume(jobIds);
        return Result.success();
    }

}
