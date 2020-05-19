package cn.acyou;

import com.google.common.collect.Lists;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2020/5/15]
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScorpioBaseTests {
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

    //发起流程
    @Test
    public void test1(){
        System.out.println("ok");
        //1. 启动流程时设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "10211");
        map.put("user1", "mg2");
        map.put("user2", "boss");
        //设置流程发起人
        Authentication.setAuthenticatedUserId("10211");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(process_Name, "BUSINESS_10211", map);
        Authentication.setAuthenticatedUserId(null);

        System.out.println("getProcessInstanceId：" + pi.getId());
        System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        System.out.println("getProcessDefinitionName：" + pi.getProcessDefinitionName());
        //getProcessInstanceId：460063852374016000
        //getProcessDefinitionId：LeaveProcess2:1:460062180079845377
        //getProcessDefinitionKey：LeaveProcess2
        //getProcessDefinitionName：LeaveProcess2
        System.out.println("流程启动成功");
    }
    //我的任务
    @Test
    public void myTask(){
        List<ProcessInstance> myTask = runtimeService.createProcessInstanceQuery().startedBy("10211").list();
        System.out.println("我的任务：");
        List<String> instanceIds = new ArrayList<>();
        for (ProcessInstance processInstance: myTask){
            instanceIds.add(processInstance.getProcessInstanceId());
        }
        List<Task> taskList = taskService.createTaskQuery().processInstanceIdIn(instanceIds).taskCandidateOrAssigned("10211").list();
        for (Task task : taskList) {
            System.out.println("taskId：" + task.getId());
            System.out.println("taskName：" + task.getName());
        }
        //taskId：460063852751503360
        //taskName：请假申请
    }
    @Test
    public void searchTask(){
        Task task = taskService.createTaskQuery().taskId("460063852751503360")
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .singleResult();
        //查询的时候要：includeProcessVariables/includeTaskLocalVariables才会有结果
        Map<String, Object> processVariables = task.getProcessVariables();
        Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
        System.out.println("ok");

    }
    @Test
    public void searchTask2(){
        //根据任务变量查询
        List<Task> list = taskService.createTaskQuery().taskAssignee("10211")
                .taskVariableValueEquals("age", "23")
                .list();
        List<Task> list2 = taskService.createTaskQuery().taskAssignee("10211")
                .processVariableValueEquals("name", "小三")
                .list();


        System.out.println("ok");
    }
    @Test
    public void commitNext(){
        //2. 完成任务时设置变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", "通过");
        variables.put("day", "2");
        taskService.complete("460073317844402176", variables);
        System.out.println("提交完成");
        //3. 通过TaskService内的Set方法设置变量
        //taskService.setVariable("459726315684118528", "name", "小三");

    }
    @Test
    public void getVariables(){
        //获取变量
        Task task = taskService.createTaskQuery().taskId("459726315684118528").singleResult();
        //1. 根据ExecutionId获取变量
        Map<String, Object> variablesMap = runtimeService.getVariables(task.getExecutionId());
        //2. 根据TaskId获取变量
        Map<String, Object> variablesMap2 = taskService.getVariables(task.getId());

        //在流程执行或者任务执行的过程中，用于设置和获取变量，使用流程变量在流程传递的过程中传递业务参数。
        //Variables是流程变量，流程中公用
        //VariablesLocal是局部变量，

        Map<String, Object> variablesLocalMap = taskService.getVariablesLocal(task.getId());

    }

    @Test
    public void myTask2(){
        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned("mg").list();
        for (Task task : taskList) {
            System.out.println("taskId：" + task.getId());
            System.out.println("taskName：" + task.getName());
        }
        //taskId：459697833084207104
        //taskName：经理审批
    }
    //撤回
    @Test
    public void cancelTask(){
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("459695204346118144")
                .moveActivityIdTo("directorTak", "fillTask")
                .changeState();
        System.out.println("操作成功");
    }
    //查询Execution
    @Test
    public void cancelTask2(){
        List<Execution> list = processEngine.getRuntimeService().createExecutionQuery().processInstanceId("459695204346118144").list();
        for (Execution execution: list){
            System.out.println(execution.getActivityId());
            System.out.println(execution.getId());
            System.out.println(execution.getName());
        }
    }
    //待我处理
    @Test
    public void waitMeProcess(){
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroupIn(Lists.newArrayList("manager"))
                .taskCandidateOrAssigned("1012");
        List<Task> waitMeProcessTasks = taskQuery.orderByTaskCreateTime().desc().list();
        System.out.println(waitMeProcessTasks);
    }
    //提交人主动撤回
    @Test
    public void cancelApply(){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("1111").singleResult();
        Task task = taskService.createTaskQuery().processInstanceId("111").taskId("1111").singleResult();

    }
    @Test
    public void test2(){
        HistoricProcessInstanceQuery instanceQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy("1012");
        List<HistoricProcessInstance> historicProcessInstances = instanceQuery
                .orderByProcessInstanceStartTime().desc()
                .listPage(0, 10);
        System.out.println(historicProcessInstances);
    }


    //用户组的
    @Test
    public void candidateGroup(){
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroupIn(Lists.newArrayList("manager"))
                .taskCandidateOrAssigned("1015");
        List<Task> waitMeProcessTasks = taskQuery.orderByTaskCreateTime().desc().list();
        System.out.println(waitMeProcessTasks);
    }





    @Test
    public void setAndGetVariables() {

        /** 设置流程变量 **/
        // runtimeService.setVariable(executionId, variableName, value);
        // runtimeService.setVariables(executionId, variables);

        // taskService.setVariable(taskId, variableName, value);
        // 使用任务Id，和流程变量的名称，设置流程变量的值
        // taskService.setVariables(taskId, variables);使用任务Id,和Map集合设置流程变量，设置多个值

        // runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);启动流程实例的同时，可以设置流程变量，使用Map集合
        // taskService.complete(taskId, variables);完成任务的同时，设置流程变量，使用Map集合

        /** 获取流程变量 **/
        // runtimeService.getVariable(executionId,variableName);使用执行对象Id和流程变量名称，获取值
        // runtimeService.getVariables(executionId);使用执行对象Id，获取所有的流程变量，返回Map集合
        // runtimeService.getVariables(executionId,variableNames);使用执行对象Id，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map中

        // taskService.getVariable(taskId,variableName);使用任务Id和流程变量名称，获取值
        // taskService.getVariables(taskId);使用任务Id，获取所有的流程变量，返回Map集合
        // taskService.getVariables(taskId,variableNames);使用任务Id，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map中
    }


}
