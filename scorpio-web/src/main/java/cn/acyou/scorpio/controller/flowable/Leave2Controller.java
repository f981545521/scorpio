package cn.acyou.scorpio.controller.flowable;

import cn.acyou.framework.model.Result;
import io.swagger.annotations.Api;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Api(tags = "Leave2工作流测试")
@Controller
@RequestMapping(value = "leave2")
public class Leave2Controller {
    private static final String process_Name = "LeaveProcess2";
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * submit
     */
    @GetMapping(value = "submit")
    @ResponseBody
    public Result submitLeave(String userId, Integer day) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        if (userId == null || day == null) {
            return Result.error("请写入请假天数.........");
        }
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "1011");
        map.put("user1", "mg");
        map.put("user2", "boss");
        //在启动流程之前调用，用以设置ACT_HI_PROCINST中的START_USER_ID_
        Authentication.setAuthenticatedUserId(userId);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(process_Name, "1011", map);
        //过排他网关
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        taskService.setVariable(task.getId(), "day", day);
        taskService.complete(task.getId());

        //老板或经理审批根据请假天数
        Task task2 = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        if ("boss".equals(task2.getAssignee())) {
            taskService.setVariable(task2.getId(), "outcome", "驳回");
            taskService.complete(task2.getId());
        } else {
            taskService.setVariable(task2.getId(), "outcome", "通过");
            taskService.complete(task2.getId());
        }
        System.out.println("{进程实例id：" + "\"" + pi.getId() + "\"" + "," + "请假申请人：" + "\"" + userId + "\"" + "," + "请假天数：" + "\"" + day + "\"" + "}");
        return Result.success();

    }

}
