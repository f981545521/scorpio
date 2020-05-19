package cn.acyou.scorpio.controller.flowable.handler;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 经理的任务处理程序
 *
 * @author youfang
 * @date 2020/05/19
 */
public class ManagerTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("经理审批===");
        delegateTask.setAssignee("经理");
    }

}
