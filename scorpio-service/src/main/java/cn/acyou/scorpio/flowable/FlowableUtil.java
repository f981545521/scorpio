package cn.acyou.scorpio.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Flowable工作流 工具
 * @author youfang
 * @version [1.0.0, 2020/5/21]
 **/
@Slf4j
@Component
public class FlowableUtil {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 启动流程实例
     *
     * @param startUserId  流程发起人
     * @param processName  进程名称
     * @param businessKey  业务关键
     * @param variablesMap 启动流程时设置变量
     */
    public String startProcessInstance(String startUserId, String processName, String businessKey, Map<String, Object> variablesMap){
        Authentication.setAuthenticatedUserId(startUserId);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName, businessKey, variablesMap);
        Authentication.setAuthenticatedUserId(null);
        log.info("启动流程实例: getProcessInstanceId：{}" , pi.getId());
        log.info("启动流程实例: getProcessDefinitionId：{}" , pi.getProcessDefinitionId());
        log.info("启动流程实例: getProcessDefinitionKey：{}" , pi.getProcessDefinitionKey());
        log.info("流程启动成功");
        return pi.getId();
    }

    /**
     * 查询流程实例
     *
     * @param businessKey 业务关键
     */
    public ProcessInstance queryProcessInstance(String businessKey){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        log.info("查询流程实例: {}", processInstance.getName());
        return processInstance;
    }

    /**
     * 完成任务
     *
     * @param taskId       任务Id
     * @param variablesMap 变量映射
     */
    public void completeTask(String taskId, Map<String, Object> variablesMap){
        taskService.complete(taskId, variablesMap);
        log.info("完成任务: {}", taskId);
    }

    /**
     * 判断流程是否完成
     * @param processInstanceId processInstanceId
     * @return 是否完成
     */
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished()
                .processInstanceId(processInstanceId).count() > 0;
    }


    /**
     * 查询任务
     *
     * @param taskId 任务Id
     * @return {@link Task}
     */
    public Task queryTask(String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId)
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .singleResult();
        //查询的时候要：includeProcessVariables/includeTaskLocalVariables才会有Variables结果
        //Map<String, Object> processVariables = task.getProcessVariables();
        //Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
        return task;
    }

    public void queryTaskByVariables(String userId, Map<String, Object> taskVariableMap, Map<String, Object> processVariableMap){
        //TODO: 根据任务变量查询
        List<Task> list = taskService.createTaskQuery().taskAssignee("10211")
                .taskVariableValueEquals("age", "23")
                .list();
        List<Task> list2 = taskService.createTaskQuery().taskAssignee("10211")
                .processVariableValueEquals("name", "小三")
                .list();
        System.out.println("ok");
    }


}
