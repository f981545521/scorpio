package cn.acyou.scorpio.dto.demo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2018-10-19 下午 12:09]
 * @since [天天健身/运动模块]
 **/
public class StudentExportEntityGroup implements Serializable {
    private static final long serialVersionUID = 3481258205345019482L;

    /**
     * 组名称
     */
    @Excel(name = "分组")
    private String groupName;

    public StudentExportEntityGroup() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
