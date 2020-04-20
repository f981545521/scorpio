package cn.acyou.scorpio.service.task;

import cn.acyou.framework.service.Service;
import cn.acyou.scorpio.mapper.task.entity.ScheduleJob;

/**
 * @author yofuang
 * @version [1.0.0, 2020-4-4 下午 10:05]
 **/
public interface ScheduleJobService extends Service<ScheduleJob> {

    void run(Long jobId);

    void pause(Long jobId);

    void resume(Long jobId);
}
