package cn.acyou.scorpio.tool.entity;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

import cn.acyou.framework.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * t_tool_param_config 实体类
 * 2020-07-06 18:07:05 参数配置表
 * @author youfang
 */ 
@Table(name = "t_tool_param_config")
public class ParamConfig extends BaseEntity {

    private static final long serialVersionUID = -2608376861382520568L;
	/**
	 * 参数主键(PK)
	 */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long	id;
	/**
	 * 参数名称
	 */
    @Column(name = "name")
    private String	name;
	/**
	 * 参数编码(键名)
	 */
    @Column(name = "code")
    private String	code;
	/**
	 * 参数键值
	 */
    @Column(name = "value")
    private String	value;
	/**
	 * 参数扩展值
	 */
    @Column(name = "ext_value")
    private String	extValue;
	/**
	 * 系统内置（1是 0否）
	 */
    @Column(name = "is_system")
    private Integer	isSystem;
	/**
	 * 是否启用（1是 0否）
	 */
    @Column(name = "status")
    private Integer	status;
	/**
	 * 备注
	 */
    @Column(name = "remark")
    private String	remark;
	/**
	 * 排序值
	 */
    @Column(name = "sort")
    private Integer	sort;
	/**
	 * 是否删除  0-正常 1-删除
	 */
    @Column(name = "is_delete")
    private Integer	isDelete;

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }

    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

    public void setExtValue(String extValue){
        this.extValue = extValue;
    }
    public String getExtValue(){
        return extValue;
    }

    public void setIsSystem(Integer isSystem){
        this.isSystem = isSystem;
    }
    public Integer getIsSystem(){
        return isSystem;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
    public Integer getStatus(){
        return status;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
    public String getRemark(){
        return remark;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
    public Integer getSort(){
        return sort;
    }

    public void setIsDelete(Integer isDelete){
        this.isDelete = isDelete;
    }
    public Integer getIsDelete(){
        return isDelete;
    }



}
