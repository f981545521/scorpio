package cn.acyou.scorpio.task.customize;

import cn.acyou.framework.utils.DateUtil;
import cn.acyou.scorpio.task.base.TaskParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author acyou
 * @version [1.0.0, 2020-4-4 下午 09:20]
 **/
@Slf4j
@Component
public class testTask extends TaskParent {

    @Override
    public void taskBody() {
        log.info("执行了testTask，时间为:" + DateUtil.getCurrentDateFormat());
    }

}
