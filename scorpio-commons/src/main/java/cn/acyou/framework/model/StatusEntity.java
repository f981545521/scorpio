package cn.acyou.framework.model;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2019-09-02 15:32]
 **/
public class StatusEntity implements Serializable {

    private static final long serialVersionUID = -444792519303908950L;
    /**
     * 编码
     */
    public Integer code;
    /**
     * 名称
     */
    public String name;

    public StatusEntity() {
    }

    public StatusEntity(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
        return "StatusEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
