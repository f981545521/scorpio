package cn.acyou.scorpio.controller.demo;

import cn.acyou.framework.model.Result;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/2]
 **/
@Controller
@RequestMapping("demo")
public class CaptchaController {
    @GetMapping("/captcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
                .style(CaptchaStyle.IMG)
                .type(CaptchaType.NUMBER)
                .length(4)
                .build()
                .finish();
    }

    @GetMapping("/verify")
    @ResponseBody
    public Result<?> verify(String code, HttpServletRequest request) {
        boolean flag = HappyCaptcha.verification(request, code, true);
        //当验证码被使用后，可以通过HappyCaptcha类种的remove()方法将Session中存放的验证码清理掉。
        HappyCaptcha.remove(request);
        if (flag) {
            return Result.success("验证通过！");
        } else {
            return Result.error("验证码不正确！");
        }
    }
}
