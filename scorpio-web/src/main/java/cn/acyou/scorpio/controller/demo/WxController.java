package cn.acyou.scorpio.controller.demo;

import cn.acyou.framework.model.Result;
import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author youfang
 * @version [1.0.0, 2020/9/2]
 **/
@Slf4j
@RestController
@RequestMapping("/wx")
@Api(value = "WxController", tags = "微信接口")
public class WxController {
    @Autowired
    private WxMaService wxMaService;

    @RequestMapping(value = "/test1", method = {RequestMethod.GET})
    @ResponseBody
    public Result<?> test1() {
        WxMaMsgService msgService = wxMaService.getMsgService();
        return Result.success();
    }
}
