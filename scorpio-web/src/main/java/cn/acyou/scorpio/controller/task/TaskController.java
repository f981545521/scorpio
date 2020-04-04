package cn.acyou.scorpio.controller.task;

import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.task.ScheduleJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 09:30]
 **/
@RestController
@Api(value = "定时任务", tags = {"动态定时任务"})
public class TaskController {

    @Autowired
    private ScheduleJobService scheduleJobService;

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
