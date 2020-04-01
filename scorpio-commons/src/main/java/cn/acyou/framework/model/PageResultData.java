package cn.acyou.framework.model;

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
public class PageResultData<T> implements Serializable {
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
     * 总记录数
     */
    private Long total;

    /**
     * 返回数据
     */
    private List<T> list;

    /**
     * 扩展数据信息（用于数据统计等...）
     */
    private Object extData;

    /**
     * @return 返回 pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum 对pageNum进行赋值
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return 返回 pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize 对pageSize进行赋值
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return 返回 total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total 对total进行赋值
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return 返回 list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list 对list进行赋值
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    /**
     * 提供方法：使用pageHelper时 转 PageResultData
     * example:
     * <code>
     * PageHelper.startPage(req.getPageNum(), req.getPageSize());
     * List<MarketingProductVo> marketingProductList = marketingProductMapper.selectMarketingProduct(req);
     * PageResultData<MarketingProductVo> pageResultData = PageResultData.convert(new PageInfo<>(marketingProductList));
     * </code>
     *
     * @param pageInfo pageHelper 分页对象
     * @param <T>      具体类型
     * @return PageResultData
     */
    public static <T> PageResultData<T> convert(PageInfo<T> pageInfo) {
        PageResultData<T> resultData = new PageResultData<>();
        resultData.setPageNum(pageInfo.getPageNum());
        resultData.setPageSize(pageInfo.getPageSize());
        resultData.setTotal(pageInfo.getTotal());
        resultData.setList(pageInfo.getList());
        return resultData;
    }

    /**
     * 提供方法：使用pageHelper时 转 PageResultData
     * example:
     * <code>
     * PageHelper.startPage(req.getPageNum(), req.getPageSize());
     * List<MarketingProductVo> marketingProductList = marketingProductMapper.selectMarketingProduct(req);
     * PageResultData<MarketingProductVo> pageResultData = PageResultData.convert(marketingProductList);
     * </code>
     *
     * @param dataList dataList 数据List
     * @param <T>      具体类型
     * @return PageResultData
     */
    public static <T> PageResultData<T> convert(List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo<>(dataList);
        return convert(pageInfo);
    }


    @Override
    public String toString() {
        return "PageResultData [pageNum=" + pageNum + ", pageSize=" + pageSize + ", total=" + total + ", list=" + list
                + "]";
    }
}
