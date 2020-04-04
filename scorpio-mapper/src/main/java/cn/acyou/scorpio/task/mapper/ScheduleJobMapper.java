package cn.acyou.scorpio.task.mapper;

import cn.acyou.framework.mapper.Mapper;
import cn.acyou.scorpio.task.entity.ScheduleJob;

import java.util.List;

/**
 * t_task_schedule_job Mapper
 * 2020-04-04 22:39:32 定时任务
 *
 * @author youfang
 */
public interface ScheduleJobMapper extends Mapper<ScheduleJob> {

    /**
     * 批量更新
     *
     * @param list 修改记录
     */
    int updateListSelective(List<ScheduleJob> list);


}
