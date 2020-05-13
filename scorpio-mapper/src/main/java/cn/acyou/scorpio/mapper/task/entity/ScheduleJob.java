package cn.acyou.scorpio.mapper.task.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * t_task_schedule_job 实体类
 * 2020-04-04 22:39:32 定时任务
 *
 * @author youfang
 */
@Table(name = "t_task_schedule_job")
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 8116055445757936100L;
    /**
     * 任务id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "job_id")
    private Long jobId;
    /**
     * spring bean名称
     */
    @Column(name = "bean_name")
    private String beanName;
    /**
     * 参数
     */
    @Column(name = "params")
    private String params;
    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;
    /**
     * 任务状态  0：正常  1：暂停
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 上次执行成功时间
     */
    @Column(name = "last_execute_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastExecuteTime;

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getParams() {
        return params;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }
}
