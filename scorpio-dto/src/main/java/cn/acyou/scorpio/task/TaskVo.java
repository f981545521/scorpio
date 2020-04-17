package cn.acyou.scorpio.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-5 上午 12:25]
 **/
@Data
public class TaskVo implements Serializable {

    private static final long serialVersionUID = -7964739865313210925L;
    private String name;

    private String simpleName;
}