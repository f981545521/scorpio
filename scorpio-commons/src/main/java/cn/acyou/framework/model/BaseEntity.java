package cn.acyou.framework.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/7]
 **/
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Long	createUser;
    /**
     * 最后修改时间
     */
    @Column(name = "last_update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date	lastUpdateTime;
    /**
     * 最后修改人
     */
    @Column(name = "last_update_user")
    private Long	lastUpdateUser;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(Long lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
}
