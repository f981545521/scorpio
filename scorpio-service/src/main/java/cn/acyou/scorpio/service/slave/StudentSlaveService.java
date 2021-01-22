package cn.acyou.scorpio.service.slave;

import cn.acyou.framework.service.Service;
import cn.acyou.scorpio.salve.entity.StudentSlave;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/22]
 **/
public interface StudentSlaveService extends Service<StudentSlave> {
    List<StudentSlave> selectStudents();
}
