package cn.acyou.scorpio.salve.mapper;

import cn.acyou.framework.mapper.Mapper;
import cn.acyou.scorpio.salve.entity.StudentSlave;

import java.util.List;

/**
 * @author youfang
 * @date 2018-04-15 下午 07:37
 **/

public interface StudentSlaveMapper extends Mapper<StudentSlave> {


    List<StudentSlave> selectStudents();

}
