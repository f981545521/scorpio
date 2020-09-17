package cn.acyou.scorpio.system.entity;

import cn.acyou.framework.mapper.tkMapper.annotation.SelectiveIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author youfang
 * @date 2018-04-15 下午 07:36
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "JDBC")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "signature")
    private String signature;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "age")
    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "birthday")
    @SelectiveIgnore
    private Date birthday;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_user")
    private Long createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private Long updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;
}
