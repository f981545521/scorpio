package cn.acyou.scorpio.system.mapper;

import cn.acyou.framework.mapper.Mapper;
import cn.acyou.scorpio.system.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * @author youfang
 * @date 2018-04-15 下午 07:37
 **/
public interface StudentMapper extends Mapper<Student> {

    void incrementAge(@Param("id") long id, @Param("delta") long delta);
}
