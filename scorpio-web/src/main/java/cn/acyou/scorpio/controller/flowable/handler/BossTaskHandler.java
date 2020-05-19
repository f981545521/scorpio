package cn.acyou.scorpio.controller.flowable.handler;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author youfang
 * @date 2020/05/19
 */
public class BossTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("老板审批===");
        delegateTask.setAssignee("老板");
    }

}
