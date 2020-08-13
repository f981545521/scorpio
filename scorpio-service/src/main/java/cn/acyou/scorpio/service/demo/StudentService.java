package cn.acyou.scorpio.service.demo;

import cn.acyou.framework.service.Service;
import cn.acyou.scorpio.system.entity.Student;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-17 下午 08:55]
 **/
public interface StudentService extends Service<Student> {

    void studentTest();

    void addAStudent();

    void flushCache(Integer id);

    void testSelectAndUpdateAndSelect();

    void addInsertAndThrowException();

    void testConcurrentOpt(Long id, Long dealt);

    void testConcurrentOptMinus(Long dealt);

    void testInsertSync();

}
