package cn.acyou.scorpio.controller.tool;

import cn.acyou.framework.model.IdsReq;
import cn.acyou.framework.model.PageData;
import cn.acyou.framework.model.Result;
import cn.acyou.framework.valid.annotation.ParamValid;
import cn.acyou.scorpio.dto.tool.param.QueryParamConfigListReq;
import cn.acyou.scorpio.tool.entity.ParamConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.acyou.scorpio.service.tool.ParamConfigService;

/**
 * t_tool_param_config SERVICE
 * 2020-07-06 18:07:05 参数配置表 接口
 * @author youfang
 */ 
@Slf4j
@RestController
@RequestMapping("/paramconfig")
@Api(value = "参数配置表", description = "参数配置表", tags = "参数配置表接口")
public class ParamConfigController {
    @Autowired
    private ParamConfigService paramconfigService;


    /**
     * 查询列表
     *
     * @param req
     */
    @PostMapping(value = "queryParamConfigByManager", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result<PageData<ParamConfig>> queryParamConfigByManager(@RequestBody QueryParamConfigListReq req) {
        log.info("[-]|{}|{}|{}", "begin", "ManagerParamConfigController.queryParamConfigByManager param", req.toString());
        try {
            //校验请求参数
            PageData<ParamConfig> pageList = paramconfigService.listParamConfig(req);
            log.info("[-]|{}|{}", "end", "ManagerParamConfigController.queryParamConfigByManager");
            return Result.success(pageList);
        } catch (Exception e) {
            log.error("[-]|{}|{}|{}", "error", "ManagerParamConfigController.queryParamConfigByManager", e);
            return Result.error();
        }
    }

    /**
     * 根据code获取单个参数配置
     */
    @PostMapping(value = "getParamConfigByManager", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result<ParamConfig> getParamConfigByManager(@RequestBody String code) {
        log.info("[-]|{}|{}|{}", "begin", "ManagerParamConfigController.getParamConfigByManager param", code);
        try {
            //校验请求参数
            ParamConfig paramConfig = paramconfigService.getParamConfigByCode(code);
            log.info("[-]|{}|{}", "end", "ManagerParamConfigController.getParamConfigByManager");
            return Result.success(paramConfig);
        } catch (Exception e) {
            log.error("[-]|{}|{}", "error", "ManagerParamConfigController.getParamConfigByManager", e);
            return Result.error();
        }
    }

    /**
     * 新增
     *
     * @param paramConfig：请求参数
     * @return
     */
    @PostMapping(value = "addParamConfigByManager", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result addParamConfigByManager(@ParamValid @RequestBody ParamConfig paramConfig) {
        log.info("[-]|{}|{}|{}", "begin", "ManagerParamConfigController.addParamConfigByManager param", paramConfig.toString());
        try {
            paramconfigService.addParamConfig(paramConfig);
            log.info("[-]|{}|{}", "end", "ManagerParamConfigController.addParamConfigByManager");
            return Result.success();
        } catch (Exception e) {
            log.error("[-]|{}|{}", "error", "ManagerParamConfigController.addParamConfigByManager", e);
            return Result.error();
        }
    }

    /**
     * 删除
     * @param id 请求参数
     */
    @PostMapping(value = "deleteParamConfigByManager", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result deleteParamConfigByManager(@RequestBody Long id) {
        log.info("[-]|{}|{}|{}", "begin", "ManagerParamConfigController.deleteParamConfigByManager param", id);
        try {
            paramconfigService.deleteParamConfig(id);
            return Result.success();
        } catch (Exception e) {
            log.error("[-]|{}|{}", "error", "ManagerParamConfigController.deleteParamConfigByManager", e);
            return Result.error();
        }
    }

    /**
     * 删除
     * @param idsReq 请求参数
     */
    @PostMapping(value = "deleteParamConfigByManagers", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result deleteParamConfigByManagers(@RequestBody IdsReq idsReq) {
        log.info("[-]|{}|{}|{}", "begin", "ManagerParamConfigController.deleteParamConfigByManagers param", idsReq);
        try {
            paramconfigService.deleteParamConfigs(idsReq.getIds());
            return Result.success();
        } catch (Exception e) {
            log.error("[-]|{}|{}", "error", "ManagerParamConfigController.deleteParamConfigByManagers", e);
            return Result.error();
        }
    }

    /**
     * 修改
     */
    @PostMapping(value = "updateParamConfigByManager", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result updateParamConfigByManager(@RequestBody ParamConfig paramConfig) {
        log.info("[-]|{}|{}|{}","begin", "ManagerParamConfigController.updateParamConfigByManager param", paramConfig.toString());
        try {
            paramconfigService.updateParamConfig(paramConfig);
            log.info("[-]|{}|{}", "end", "ManagerParamConfigController.updateParamConfigByManager");
            return Result.success();
        } catch (Exception e) {
            log.error("[-]|{}|{}", "error", "ManagerParamConfigController.updateParamConfigByManager", e);
            return Result.error();
        }
    }

}
