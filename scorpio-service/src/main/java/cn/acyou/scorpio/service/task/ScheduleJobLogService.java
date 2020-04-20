package cn.acyou.scorpio.service.task;

import cn.acyou.framework.service.Service;
import cn.acyou.scorpio.mapper.task.entity.ScheduleJob;
import cn.acyou.scorpio.mapper.task.entity.ScheduleJobLog;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 10:59]
 **/
public interface ScheduleJobLogService extends Service<ScheduleJobLog> {

    void success(ScheduleJob scheduleJob, String remark, Integer times);

    void error(ScheduleJob scheduleJob, String errorMessage);
}
