package cn.acyou.scorpio.service.task.customize;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.scorpio.service.task.base.TaskParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-4 下午 09:20]
 **/
@Slf4j
@Component
public class MyDynamicTask extends TaskParent {

    @Override
    public void run(String params) {
        log.info("执行了MyDynamicTask，时间为:" + DateUtil.getCurrentDateFormat());
    }
}
