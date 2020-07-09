package cn.acyou.framework.model;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/6]
 **/
public class PageSo implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 当前记录起始索引 */
    private Integer pageNum = 1;
    /** 每页显示记录数 */
    private Integer pageSize = 10;
    /** 排序 */
    private String sorts;

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

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

}
