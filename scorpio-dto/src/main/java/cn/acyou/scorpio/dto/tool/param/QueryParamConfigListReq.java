package cn.acyou.scorpio.dto.tool.param;

import java.io.Serializable;

/**
 * 参数配置查询参数
 *
 * @author You Fang
 * @version [1.0.0, 2018年2月14日]
 * @since [参数配置]
 */
public class QueryParamConfigListReq implements Serializable {

    private static final long serialVersionUID = 2405242324703912302L;
    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String	name;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueryParamConfigListReq{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
