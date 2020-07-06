package cn.acyou.scorpio.service.tool;

import cn.acyou.framework.model.PageData;
import cn.acyou.framework.service.Service;
import cn.acyou.scorpio.dto.tool.param.QueryParamConfigListReq;
import cn.acyou.scorpio.tool.entity.ParamConfig;

import java.util.List;

/**
 * t_tool_param_config SERVICE
 * 2020-07-06 18:07:05 参数配置表 服务
 * @author youfang
 */ 
public interface ParamConfigService extends Service<ParamConfig> {

    /**
     * 根据条件分页获取参数配置列表
     *
     * @param req：请求参数
     * @return
     */
    PageData<ParamConfig> listParamConfig(QueryParamConfigListReq req);

    /**
     * 根据编码获取单个编码详情
     *
     * @param code：编码数据
     * @return
     */
    ParamConfig getParamConfigByCode(String code);

    /**
     * 新增数据参数配置
     *
     * @param paramConfig：编码数据
     * @return
     */
    int addParamConfig(ParamConfig paramConfig);

    /**
     * 根据ID删除参数配置
     *
     * @param id 主键ID
     * @return
     */
    int deleteParamConfig(Long id);

    /**
     * 根据IDS批量删除参数配置
     *
     * @param ids 主键ID
     * @return
     */
    int deleteParamConfigs(List<Long> ids);

    /**
     * 根据ID修改参数配置
     *
     * @param paramConfig：参数配置
     * @return
     */
    int updateParamConfig(ParamConfig paramConfig);
}
