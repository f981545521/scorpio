package cn.acyou.framework.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SQL_DOT = ".";

    private String alisaPrefix = null;
    private String orderField;
    private OrderType orderType = OrderType.ASC;

    public Sort() {
    }

    public Sort(String orderField, OrderType orderType, String alisaPrefix) {
        assert orderField != null;
        this.orderField = orderField;
        this.orderType = orderType;
        this.alisaPrefix = alisaPrefix;
    }


    public Sort(String orderField) {
        this(orderField, OrderType.ASC, null);
    }

    /**
     * 如：http://localhost:8033/boss/student?sorts=age-desc&currentPage=1&pageSize=3
     *
     * @param sortString 排序字段
     * @return Sort
     */
    public static Sort valueOf(String sortString) {
        String[] sortParams = sortString.split("-");
        Sort result = null;
        if (sortParams.length > 0) {
            String orderField = sortParams[0];
            OrderType orderType = OrderType.ASC;
            boolean enablePrefix = true;
            if (sortParams.length > 1) {
                orderType = OrderType.valueOf(sortParams[1].toUpperCase());
            }
            if (sortParams.length > 2) {
                enablePrefix = Boolean.valueOf(sortParams[2]);
            }
            result = new Sort(orderField.replaceAll("[A-Z]", "_$0").toLowerCase(), orderType, null);
        }
        return result;
    }

    public static Sort valueOf(String sortKey, OrderType orderType) {
        return new Sort(sortKey, orderType, null);
    }

    public static Sort valueOf(String sortKey, OrderType orderType, String alisaPrefix) {
        return new Sort(sortKey, orderType, alisaPrefix);
    }

    public String getOrderField() {
        if (StringUtils.isEmpty(alisaPrefix)){
            return orderField;
        }else {
            return alisaPrefix + SQL_DOT + orderField;
        }

    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return getOrderField() + " " + orderType;
    }
}
