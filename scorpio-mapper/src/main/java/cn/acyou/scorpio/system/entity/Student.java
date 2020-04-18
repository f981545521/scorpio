package cn.acyou.scorpio.system.entity;

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
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 5350645545628778721L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")//@KeySql(useGeneratedKeys = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "birth")
    private Date birth;

}
