package cn.acyou.scorpio.service.tool.impl;

import cn.acyou.framework.model.PageData;
import cn.acyou.framework.utils.FastJsonUtil;
import cn.acyou.framework.utils.redis.RedisUtils;
import cn.acyou.scorpio.dto.tool.constant.ToolRedisKey;
import cn.acyou.scorpio.dto.tool.param.QueryParamConfigListReq;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.acyou.framework.service.ServiceImpl;
import cn.acyou.scorpio.tool.entity.ParamConfig;
import cn.acyou.scorpio.tool.mapper.ParamConfigMapper;
import cn.acyou.scorpio.service.tool.ParamConfigService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * t_tool_param_config SERVICE
 * 2020-07-06 18:07:05 参数配置表 服务
 *
 * @author youfang
 */
@Slf4j
@Service
public class ParamConfigServiceImpl extends ServiceImpl<ParamConfigMapper, ParamConfig> implements ParamConfigService {

    @Autowired
    private ParamConfigMapper paramConfigMapper;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 根据条件分页获取参数配置列表
     *
     * @param req ：请求参数
     * @return
     */
    @Override
    public PageData<ParamConfig> listParamConfig(QueryParamConfigListReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 10 : req.getPageSize();
        PageData<ParamConfig> list = new PageData<>(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Example exp = new Example(ParamConfig.class);
        Example.Criteria criteria = exp.createCriteria();
        if (StringUtils.isNotEmpty(req.getName())) {
            criteria.andLike("name", "%" + req.getName() + "%");
        }
        if (StringUtils.isNotEmpty(req.getCode())) {
            criteria.andLike("code", "%" + req.getCode() + "%");
        }
        exp.setOrderByClause("sort desc");
        List<ParamConfig> toolDictionaryList = paramConfigMapper.selectByExample(exp);

        list.setTotal(((Page<ParamConfig>) toolDictionaryList).getTotal());
        list.setList(toolDictionaryList);
        list.setPageNum(pageNum);
        list.setPageSize(pageSize);
        return list;
    }

    /**
     * 根据编码获取单个编码详情
     *
     * @param code ：编码数据
     * @return
     */
    @Override
    public ParamConfig getParamConfigByCode(String code) {
        ParamConfig paramConfig = null;
        String redisKey = ToolRedisKey.TOOL_PARAM_CONFIG_INFO + code;
        String redisResult = redisUtil.get(redisKey);
        if (StringUtils.isNotBlank(redisResult)) {
            paramConfig = FastJsonUtil.jsonStrToBean(redisResult, ParamConfig.class);
            return paramConfig;
        }
        //从数据库中查询用户数据
        log.info("[-]|{}|{}", "ParamConfigServiceImpl.getParamConfigByCode", "by DB");
        paramConfig = new ParamConfig();
        paramConfig.setCode(code);
        paramConfig = paramConfigMapper.selectOne(paramConfig);
        if (paramConfig != null && paramConfig.getCode() != null) {
            redisUtil.set(redisKey, FastJsonUtil.toJsonString(paramConfig));
        }
        return paramConfig;
    }

    /**
     * 新增数据参数配置
     *
     * @param paramConfig 参数配置
     * @return
     */
    @Override
    public int addParamConfig(ParamConfig paramConfig) {
        return paramConfigMapper.insertSelective(paramConfig);
    }

    /**
     * 根据ID删除参数配置
     *
     * @param id 主键ID
     * @return
     */
    @Override
    public int deleteParamConfig(Long id) {
        ParamConfig paramConfig = paramConfigMapper.selectByPrimaryKey(id);
        if (paramConfig != null) {
            String redisKey = ToolRedisKey.TOOL_PARAM_CONFIG_INFO + paramConfig.getCode();
            redisUtil.delete(redisKey);
        }
        return paramConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据IDS批量删除参数配置
     *
     * @param ids 主键ID
     * @return
     */
    @Override
    public int deleteParamConfigs(List<Long> ids) {
        List<ParamConfig> paramConfigs = paramConfigMapper.selectByPrimaryKeyList(ids);
        if (CollectionUtils.isNotEmpty(paramConfigs)) {
            for (ParamConfig paramConfig : paramConfigs) {
                String redisKey = ToolRedisKey.TOOL_PARAM_CONFIG_INFO + paramConfig.getCode();
                redisUtil.delete(redisKey);
            }
        }
        return paramConfigMapper.deleteByIds(StringUtils.join(ids, ","));
    }

    /**
     * 根据ID修改参数配置
     *
     * @param paramConfig ：参数配置
     * @return
     */
    @Override
    public int updateParamConfig(ParamConfig paramConfig) {
        if (StringUtils.isNotEmpty(paramConfig.getCode())) {
            String redisKey = ToolRedisKey.TOOL_PARAM_CONFIG_INFO + paramConfig.getCode();
            redisUtil.delete(redisKey);
        }
        return paramConfigMapper.updateByPrimaryKeySelective(paramConfig);
    }

}
