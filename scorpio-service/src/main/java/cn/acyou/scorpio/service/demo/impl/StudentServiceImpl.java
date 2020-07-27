package cn.acyou.scorpio.service.demo.impl;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.service.demo.StudentService;
import cn.acyou.scorpio.system.entity.Student;
import cn.acyou.scorpio.system.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-17 下午 08:56]
 **/
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void studentTest() {
        Example example = builderExample()
                .where(Sqls.custom()
                        .andGreaterThan("birth", DateUtil.parseDate("2020-04-01")))
                .orderByDesc("age")
                .build();
        List<Student> students = baseMapper.selectByExample(example);
        System.out.println(students);
        super.updateByPrimaryKeySelective(students.get(0));
        this.updateByPrimaryKeySelective(students.get(0));
        Example build = Condition.builder(Student.class).build();
    }

    @Override
    public void addAStudent() {
        Student student = new Student();
        student.setName(RandomUtil.randomUserName());
        student.setAge(RandomUtil.randomAge());
        student.setBirth(new Date());
        baseMapper.insert(student);
        int i = 1/0;
    }

    @Override
    @Cacheable(value="sys:student#100", key="#id")
    public Student selectByPrimaryKey(Object id) {
        System.out.println("缓存测试：根据主键查找：" + id);
        return super.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value="sys:student", key="#id")
    public void flushCache(Integer id) {
        System.out.println("缓存测试：根据主键删除缓存：" + id);
    }

    @Override
    public void testSelectAndUpdateAndSelect() {
        Integer userId = 7;
        Student dbStudent = baseMapper.selectByPrimaryKey(userId);
        Student updateStudent = new Student();
        updateStudent.setId(userId);
        updateStudent.setName(RandomUtil.randomUserName());
        baseMapper.updateByPrimaryKeySelective(updateStudent);

        Student student = baseMapper.selectByPrimaryKey(userId);
        System.out.println("最新的数据");
        System.out.println(student);
    }

    @Override
    public void addInsertAndThrowException() {
        // 已经有了事务，即使下面的方法 NOT_SUPPORTED 事务也不会回滚
        // transactionNotSupportAdd();
        taskExecutor.execute(this::transactionNotSupportAdd);

        throw new ServiceException("用户不存在！");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.DEFAULT)
    public void transactionNotSupportAdd(){
        Student addStudent = new Student();
        addStudent.setName(RandomUtil.randomUserName());
        addStudent.setAge(RandomUtil.randomAge());
        addStudent.setBirth(new Date());
        this.insertSelective(addStudent);
        System.out.println(addStudent);
    }

    /**
     * 测试结果：
     * 不同线程修改同一条记录，会等待之前事务提交
     * 不同线程修改不同记录，没有影响
     *
     * 并发访问同一个接口也是一样的，修改同一条数据会等之前的提交。修改不同的记录就是立即执行没有影响
     *
     */
    @Override
    @Transactional
    public void testConcurrentOpt(Long id, Long dealt) {
        log.info("——————————————————测试并发修改与回滚——————————————————" + dealt);
        if (dealt > 0){
            synIncr(id, dealt);
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("休息结束，线程完成");
        }else {
            synIncr(id, dealt);
            log.info("不休息，线程完成");
        }
    }
    @Override
    @Transactional
    public void testConcurrentOptMinus(Long dealt) {
        log.info("——————————————————测试并发修改与回滚——————————————————" + dealt);
        if (dealt > 0){
            synIncr(1L, dealt);
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("休息结束，线程完成");
        }else {
            synIncr(1L, dealt);
            log.info("不休息，线程完成");
        }
    }

    private void synIncr(Long id, long delta){
        baseMapper.incrementAge(id, delta);
    }

}
