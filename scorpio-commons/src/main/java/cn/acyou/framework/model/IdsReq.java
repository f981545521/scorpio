package cn.acyou.framework.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/6]
 **/
@Data
public class IdsReq implements Serializable {
    private static final long serialVersionUID = -164546233529005210L;
    private List<Long> ids;


}
