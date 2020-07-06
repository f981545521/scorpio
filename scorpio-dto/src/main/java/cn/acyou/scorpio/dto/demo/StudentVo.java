package cn.acyou.scorpio.dto.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/6]
 **/
@Data
public class StudentVo implements Serializable {

    private Integer id;

    private String name;

    private Integer age;
}
