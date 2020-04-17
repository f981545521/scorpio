package cn.acyou.framework.service;

import cn.acyou.framework.mapper.Mapper;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service 实现
 *
 * @author youfang
 * @version [1.0.0, 2020/4/17]
 **/
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class ServiceImpl<M extends Mapper<T>, T> implements Service<T> {

    private final static Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    protected M baseMapper;

    private Class<T> clazz;

    public ServiceImpl() {
        TypeToken<T> poType = new TypeToken<T>(getClass()) {
        };
        clazz = (Class<T>) poType.getRawType();
    }

    /**
     * 构建查询Example
     * <pre>
     *          Example example = builderExample()
     *              .where(Sqls.custom()
     *                      .andGreaterThan("birth", DateUtil.parseDate("2020-04-01")))
     *              .orderByDesc("age")
     *              .build();
     *         List<Student> students = baseMapper.selectByExample(example);
     * </pre>
     *
     * @return 参数构造器
     */
    public Example.Builder builderExample() {
        return Example.builder(clazz);
    }

    /*
     * ————————————————————————————         INSERT   ——————————————————————————————————————————
     */

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record 记录
     * @return 影响行数
     */
    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record 记录
     * @return 影响行数
     */
    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList 记录List
     * @return 影响行数
     */
    @Override
    public int insertList(List<T> recordList) {
        return baseMapper.insertList(recordList);
    }

    /*
     * ————————————————————————————         UPDATE   ——————————————————————————————————————————
     */

    /**
     * 批量更新（属性为null不更新）
     *
     * @param recordList 记录List
     */
    @Override
    public int updateListSelective(Collection<T> recordList) {
        return baseMapper.updateListSelective(recordList);
    }

    /**
     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
     *
     * @param record  记录
     * @param example 条件
     * @return 影响行数
     */
    @Override
    public int updateByExample(T record, Object example) {
        return baseMapper.updateByExample(record, example);
    }

    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param record  记录
     * @param example 条件
     * @return 影响行数
     */
    @Override
    public int updateByExampleSelective(T record, Object example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record 记录
     * @return 影响行数
     */
    @Override
    public int updateListSelective(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param record 记录
     * @return 影响行数
     */
    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    /*
     * ————————————————————————————         DELETE   ——————————————————————————————————————————
     */

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 主键集合 如 List<Long>
     * @return 影响行数 影响行数
     */
    @Override
    public int deleteByPrimaryKeyList(Collection<Object> idList) {
        return baseMapper.deleteByPrimaryKeyList(idList);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param record 记录
     * @return 影响行数
     */
    @Override
    public int delete(T record) {
        return baseMapper.delete(record);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key 主键
     * @return 影响行数
     */
    @Override
    public int deleteByPrimaryKey(Object key) {
        return baseMapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据Example条件删除数据
     *
     * @param example 条件
     * @return 影响行数
     */
    @Override
    public int deleteByExample(Object example) {
        return baseMapper.deleteByExample(example);
    }

    /**
     * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return 影响行数
     */
    @Override
    public int delete(String ids) {
        return baseMapper.deleteByIds(ids);
    }

    /*
     * ————————————————————————————        SELECT   ——————————————————————————————————————————
     */

    /**
     * 根据主键List进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param idList 主键集合。 如 List<Long>
     * @return 查询结果
     */
    @Override
    public List<T> selectPrimaryKeyList(Collection<Object> idList) {
        return baseMapper.selectPrimaryKeyList(idList);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record 记录
     * @return 查询结果
     */
    @Override
    public List<T> select(T record) {
        return baseMapper.select(record);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example 条件
     * @return 查询结果
     */
    @Override
    public List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example)
    }

    /**
     * 查询全部结果
     *
     * @return 查询结果
     */
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param ids 如 "1,2,3,4"
     * @return 查询结果
     */
    @Override
    public List<T> selectByIds(String ids) {
        return baseMapper.selectByIds(ids);
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record 记录
     * @return 查询结果
     */
    @Override
    public int selectCount(T record) {
        return baseMapper.selectCount(record);
    }

    /**
     * 根据Example条件进行查询总数
     *
     * @param example 条件
     * @return 查询结果
     */
    @Override
    public int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return 查询结果
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        return baseMapper.selectByPrimaryKey(key);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record 记录
     * @return 查询结果
     */
    @Override
    public T selectOne(T record) {
        return baseMapper.selectOne(record);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example 条件
     * @return 查询结果
     */
    @Override
    public T selectOneByExample(Object example) {
        return baseMapper.selectOneByExample(example);
    }


}
