package cn.acyou.scorpio.tool.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * t_task_schedule_job_log 实体类
 * 2020-04-04 23:23:44 定时任务日志
 *
 * @author youfang
 */
@Table(name = "t_task_schedule_job_log")
public class ScheduleJobLog implements Serializable {

    private static final long serialVersionUID = 3801632289520985775L;
    /**
     * 任务日志id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "log_id")
    private Long logId;
    /**
     * 任务id
     */
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
     * 任务状态    0：成功    1：失败
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 失败信息
     */
    @Column(name = "error")
    private String error;
    /**
     * 耗时(单位：毫秒)
     */
    @Column(name = "times")
    private Integer times;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogId() {
        return logId;
    }

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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getTimes() {
        return times;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}
