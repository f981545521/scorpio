package cn.acyou.scorpio.dto.task;

import cn.acyou.framework.model.StatusEntity;
import cn.acyou.framework.utils.AppStatusUtil;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2019-10-08 11:15]
 **/
public class TaskAppStatus {
    /**
     * 商品状态
     */
    public static class ProductStatus {
        public static final StatusEntity CG = new StatusEntity(0, "草稿");
        public static final StatusEntity DSH = new StatusEntity(1, "待审核");
        public static final StatusEntity SHBTG = new StatusEntity(2, "审核不通过");
        public static final StatusEntity SHTG = new StatusEntity(3, "审核通过");

        public static List<StatusEntity> listAllStatus() {
            return AppStatusUtil.listAllStatus(ProductStatus.class);
        }

        public static String getStatusName(Integer code) {
            return AppStatusUtil.getStatusName(ProductStatus.class, code);
        }
    }
}
