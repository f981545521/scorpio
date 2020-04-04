package cn.acyou.scorpio.controller.task;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.task.ScheduleJobService;
import cn.acyou.scorpio.task.TaskVo;
import cn.acyou.scorpio.task.base.ITask;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 09:30]
 **/
@RestController
@Api(value = "定时任务", tags = {"动态定时任务"})
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
            taskVo.setSimpleName(StringUtils.uncapitalize(task.getClass().getSimpleName()));
            taskVoList.add(taskVo);
        }
        return Result.success(taskVoList);
    }
    @GetMapping("/run")
    @ApiOperation(value = "立即执行任务")
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
