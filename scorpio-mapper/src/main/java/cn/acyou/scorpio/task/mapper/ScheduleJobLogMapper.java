package cn.acyou.scorpio.task.mapper;

import cn.acyou.framework.mapper.Mapper;
import cn.acyou.scorpio.task.entity.ScheduleJobLog;

import java.util.List;

/**
 * t_task_schedule_job_log Mapper
 * 2020-04-04 23:23:44 定时任务日志
 *
 * @author youfang
 */
public interface ScheduleJobLogMapper extends Mapper<ScheduleJobLog> {

    /**
     * 批量更新
     *
     * @param list 修改记录
     */
    int updateListSelective(List<ScheduleJobLog> list);


}
