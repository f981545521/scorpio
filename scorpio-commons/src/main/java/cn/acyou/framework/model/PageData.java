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

    public PageData() {

    }

    public PageData(Integer pageNum, Integer pageSize, Long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        processNextPage();
    }

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


    public Integer getPageNum() {
        return pageNum;
    }


    public void setPageNum(Integer pageNum) {
        pageNum = pageNum != 0 ? pageNum : 1;
        this.pageNum = pageNum;
        processNextPage();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        processNextPage();
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
        PageData<T> resultData = new PageData<>();
        //这里没有数据的时候pageNum是0
        resultData.setPageNum(pageInfo.getPageNum() != 0 ? pageInfo.getPageNum() : 1);
        resultData.setPageSize(pageInfo.getPageSize());
        resultData.setTotal(pageInfo.getTotal());
        resultData.setList(pageInfo.getList());
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


    @Override
    public String toString() {
        return "分页数据 ： [pageNum = " + pageNum + ", pageSize = " + pageSize  + ", 共 " + totalPage + " 页，" + total + " 条记录]";
    }
}