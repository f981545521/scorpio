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
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "1021");
        map.put("user1", "mg");
        map.put("user2", "boss");
        //设置流程发起人
        Authentication.setAuthenticatedUserId("1021");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(process_Name, "BUSINESS_1021", map);
        Authentication.setAuthenticatedUserId(null);
        System.out.println("getProcessInstanceId：" + pi.getId());
        System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        System.out.println("getProcessDefinitionName：" + pi.getProcessDefinitionName());
        //getProcessInstanceId：459724801821392896
        //getProcessDefinitionId：LeaveProcess2:5:459724798805688321
        //getProcessDefinitionKey：LeaveProcess2
        //getProcessDefinitionName：LeaveProcess2
        System.out.println("流程启动成功");
    }
    //我的任务
    @Test
    public void myTask(){
        List<ProcessInstance> myTask = runtimeService.createProcessInstanceQuery().startedBy("1021").list();
        System.out.println("我的任务：");
        List<String> instanceIds = new ArrayList<>();
        for (ProcessInstance processInstance: myTask){
            instanceIds.add(processInstance.getProcessInstanceId());
        }
        List<Task> taskList = taskService.createTaskQuery().processInstanceIdIn(instanceIds).taskCandidateOrAssigned("1021").list();
        for (Task task : taskList) {
            System.out.println("taskId：" + task.getId());
            System.out.println("taskName：" + task.getName());
        }
        //taskId：459695204513890304
        //taskName：请假申请
    }
    @Test
    public void commitNext(){
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "我提交的");
        taskService.complete("459726315684118528", variables);
        System.out.println("提交完成");
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


}
