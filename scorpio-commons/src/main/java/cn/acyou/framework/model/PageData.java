package cn.acyou.framework.model;

import cn.acyou.framework.utils.BeanCopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息返回实体
 *
 * @author youfang
 * @version [1.0.0, 2019年11月23日]
 * @since [Framework]
 */
public class PageData<T> implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页显示条数
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 有下一页
     */
    private boolean hasNextPage = true;

    /**
     * 返回数据
     */
    private List<T> list;

    /**
     * 扩展数据信息（用于数据统计等...）
     */
    private Object extData;

    /* Constructor */

    public PageData(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /* GET AND SET **/

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        processNextPage();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    /* Process */

    public void processNextPage() {
        if (this.pageNum != null && this.pageSize != null && this.total != null) {
            int totalPage;
            if (pageSize == 0){
                totalPage = this.total.intValue();
                this.hasNextPage = false;
            }else {
                totalPage = (int) ((total + pageSize - 1) / pageSize);
                if (pageNum >= totalPage) {
                    this.hasNextPage = false;
                }
            }
            this.totalPage = totalPage;
        }
    }

    /* ToString */

    @Override
    public String toString() {
        return "分页数据 ： " +
                "[" +
                "第 " + pageNum + " 页, " +
                "每页显示 " + pageSize + " 条, " +
                "当前页 " + (list != null ? list.size() : 0) + " 条记录，" +
                "共 " + totalPage + " 页，" +
                "共 " + total + " 条记录" +
                "]";
    }

    /**
     * 提供方法：使用pageHelper时 转 PageData
     * example:
     * <code>
     * PageHelper.startPage(req.getPageNum(), req.getPageSize());
     * List<MarketingProductVo> marketingProductList = marketingProductMapper.selectMarketingProduct(req);
     * PageData<MarketingProductVo> PageData = PageData.convert(new PageInfo<>(marketingProductList));
     * </code>
     *
     * @param pageInfo pageHelper 分页对象
     * @param <T>      具体类型
     * @return PageData
     */
    public static <T> PageData<T> convert(PageInfo<T> pageInfo) {
        //这里没有数据的时候pageNum是0
        Integer pageNum = pageInfo.getPageNum() != 0 ? pageInfo.getPageNum() : 1;
        PageData<T> resultData = new PageData<>(pageNum, pageInfo.getPageSize());
        resultData.setTotal(pageInfo.getTotal());
        resultData.setList(pageInfo.getList());
        resultData.setTotalPage(pageInfo.getPages());
        return resultData;
    }

    /**
     * 同上（包含类型转换）
     */
    public static <T, TAR> PageData<TAR> convert(PageInfo<T> pageInfo, Class<TAR> tarClass) {
        //这里没有数据的时候pageNum是0
        Integer pageNum = pageInfo.getPageNum() != 0 ? pageInfo.getPageNum() : 1;
        PageData<TAR> resultData = new PageData<>(pageNum, pageInfo.getPageSize());
        resultData.setTotal(pageInfo.getTotal());
        resultData.setList(BeanCopyUtil.copyList(pageInfo.getList(), tarClass));
        resultData.setTotalPage(pageInfo.getPages());
        return resultData;
    }

    /**
     * 提供方法：使用pageHelper时 转 PageData
     * example:
     * <pre>
     * PageHelper.startPage(req.getPageNum(), req.getPageSize());
     * List<MarketingProductVo> marketingProductList = marketingProductMapper.selectMarketingProduct(req);
     * PageData<MarketingProductVo> PageData = PageData.convert(marketingProductList);
     * </pre>
     *
     * @param dataList dataList 数据List
     * @param <T>      具体类型
     * @return PageData
     */
    public static <T> PageData<T> convert(List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo<>(dataList);
        return convert(pageInfo);
    }

    /**
     * 开启分页
     * 配合：{@link #selectMapper 使用}
     * example:
     * <pre>
     *    PageData<Student> convert2 =  PageData.startPage(pageNum, pageSize).selectMapper(studentService.selectAll());
     *    PageData<StudentVo> convertType =  PageData.startPage(pageNum, pageSize).selectMapper(studentService.selectAll(), StudentVo.class);
     * </pre>
     * @param pageNum 页码，从1开始
     * @param pageSize 页面大小
     * @param <T> 泛型
     * @return PageData
     */
    public static <T> PageData<T> startPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return new PageData<>(pageNum, pageSize);
    }

    /**
     * 参考： {@link #startPage(Integer, Integer)}
     * @param pageNum 页码，从1开始
     * @param pageSize 页面大小
     * @param pageSizeZero  true且pageSize=0时返回全部结果，false时分页,null时用默认{false}配置
     * @param <T> 泛型
     * @return PageData
     */
    public static <T> PageData<T> startPage(Integer pageNum, Integer pageSize, Boolean pageSizeZero){
        PageHelper.startPage(pageNum, pageSize, true, null, pageSizeZero);
        return new PageData<>(pageNum, pageSize);
    }
    /**
     * 分页查询
     * @param queryList 查询结果
     * @param <ST> 泛型
     * @return PageData
     */
    public <ST> PageData<ST> selectMapper(List<ST> queryList) {
        PageInfo<ST> pageInfo = new PageInfo<>(queryList);
        return convert(pageInfo);
    }
    /**
     * 分页查询（包含类型转换）
     * @param queryList 查询结果
     * @param tarClass 模板类型
     * @param <ST> 泛型
     * @return PageData
     */
    public <ST, TAR> PageData<TAR> selectMapper(List<ST> queryList, Class<TAR> tarClass) {
        PageInfo<ST> pageInfo = new PageInfo<>(queryList);
        return convert(pageInfo, tarClass);
    }
}