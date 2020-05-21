package cn.acyou;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author youfang
 * @version [1.0.0, 2020/5/15]
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowableFormTests {
    private static final String process_Name = "LeaveProcessForm";
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
    @Autowired
    private FormRepositoryService formRepositoryService;


    //发起流程
    @Test
    public void startProcessInstance(){
        //1. 启动流程时设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("processName", "我要请假");
        map.put("userId", "10212");
        map.put("userName", "小王");
        map.put("day", "2");
        //设置流程发起人
        Authentication.setAuthenticatedUserId("10212");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(process_Name, "BUSINESS_10212", map);
        Authentication.setAuthenticatedUserId(null);

        System.out.println("getProcessInstanceId：" + pi.getId());
        System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        System.out.println("流程启动成功");
        //getProcessInstanceId：460175498765418496
        //getProcessDefinitionId：LeaveProcessForm:1:460175494944407552
        //getProcessDefinitionKey：LeaveProcessForm
    }


    //发起流程
    @Test
    public void startProcessInstanceWithForm(){
        //1. 启动流程时设置变量
        HashMap<String, Object> formProperties = new HashMap<>();
        formProperties.put("processName", "我要请假");
        formProperties.put("userId", "10214");
        formProperties.put("userName", "小王");
        formProperties.put("day", "2");
        //设置流程发起人
        Authentication.setAuthenticatedUserId("10214");

        //启动实例并且设置表单的值
        String outcome = "LeaveProcessForm";

        String processInstanceName = "LeaveProcessForm";

        ProcessInstance pi = runtimeService.startProcessInstanceWithForm("LeaveProcessForm:2:460798439651819520", outcome, formProperties, processInstanceName);

        Authentication.setAuthenticatedUserId(null);

        System.out.println("getProcessInstanceId：" + pi.getId());
        System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        System.out.println("流程启动成功");
        //getProcessInstanceId：460799580913549312
        //getProcessDefinitionId：LeaveProcessForm:2:460798439651819520
        //getProcessDefinitionKey：LeaveProcessForm
    }


    /**
     * 流程中的表单有两种：流程开始表单和流程中表单
     */
    @Test
    public void testGetFormInfo(){
        //FormInfo startFormModel = runtimeService.getStartFormModel("LeaveProcessForm:1:460808572431777792", "460799580913549312");
        //System.out.println(startFormModel);
        String startFormKey = processEngine.getFormService().getStartFormKey("LeaveProcessForm:1:460809949841211392");
        //startFormKey是外部的表单KEY
        System.out.println(startFormKey);
        StartFormData startFormData = processEngine.getFormService().getStartFormData("LeaveProcessForm:1:460809949841211392");
        System.out.println(startFormData);
        //FormProperties是在XML中直接定义的
        System.out.println(startFormData.getFormProperties());

        //获取Task 的Form
        FormInfo taskFormModel = taskService.getTaskFormModel("460799581261676544");

        //FormInfo info = runtimeService.getStartFormModel("LeaveProcessForm:1:460421156705419264",
        //        "460427011685367808");
        //org.flowable.common.engine.api.FlowableIllegalArgumentException: Form engine is not initialized
    }

    @Test
    public void testGetFormInfo2(){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("LeaveProcessForm:2:460798439651819520")
                .latestVersion().singleResult();
        StartFormData form = processEngine.getFormService().getStartFormData(processDefinition.getId());
        FormInfo info = formRepositoryService.getFormModelByKey(form.getFormKey());
        System.out.println(info);
    }

    public void startProcessInstance2(){
        //1. 启动流程时设置变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "10213");
        map.put("processName", "我要请假");
        //设置流程发起人
        Authentication.setAuthenticatedUserId("10213");
        ProcessInstance pi = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(process_Name)
                .businessKey("BUSINESS_10213")
                .variables(map)
                .name("10213请假流程")
                .start();
        Authentication.setAuthenticatedUserId(null);

        System.out.println("getProcessInstanceId：" + pi.getId());
        System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
        System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        //getProcessInstanceId：460078069869199360
        //getProcessDefinitionId：LeaveProcess2:2:460072536642699265
        //getProcessDefinitionKey：LeaveProcess2
        System.out.println("流程启动成功");
    }

    @Test
    public void test23(){
        //查询ProcessInstance
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceBusinessKey("BUSINESS_10212")
                .singleResult();
        System.out.println(processInstance.getName());
        System.out.println(processInstance);

    }
    @Test
    public void commitTask(){
        //用户提交审批
        taskService.complete("460429863644315648");
        System.out.println("提交完成");
    }

    @Test
    public void completeTask(){
        //经理审批
        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", "通过");
        taskService.complete("460114946588426240", variables);
        System.out.println("提交完成");
    }
    @Test
    public void getMyStartProcint(){
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .finished()// 已完成的 unfinish 未完成的，或者不加表示全部
                .startedBy("10212")
                .orderByProcessInstanceStartTime().asc()
                .list();
        System.out.println(list);

        //查询指定用户参与的流程信息 （流程历史  用户参与 ）
        List<HistoricProcessInstance> userJoinProcess = historyService.createHistoricProcessInstanceQuery()
                .involvedUser("10212")
                .orderByProcessInstanceStartTime().desc()
                .list();
        //判断流程是否完成
        boolean finished = isFinished("460114657118535680");

        //查询流程
        List<HistoricProcessInstance> historicDetail = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId("460114657118535680")
                .startedBy("10212")
                .finished()
                .list();

        System.out.println(historicDetail);

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId("460114657118535680").list();
        System.out.println(historicActivityInstances);

        Set<String> activityTypes = Sets.newHashSet("startEvent", "userTask", "sequenceFlow", "endEvent");
        List<HistoricActivityInstance> historicActivityInstances2 = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId("460114657118535680")
                .activityTypes(activityTypes)
                .list();
        System.out.println(historicActivityInstances2);
    }

    @Test
    public void drawProcessImage() throws Exception{
        /**
         * 获得当前活动的节点
         */
        String processDefinitionId = "";
        String processId = "460114657118535680";
        if (this.isFinished(processId)) {// 如果流程已经结束，则得到结束节点
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();

            processDefinitionId=pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            processDefinitionId=pi.getProcessDefinitionId();
        }
        List<String> highLightedActivitis = new ArrayList<String>();

        /**
         * 获得活动的节点
         */
        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        List<String> flows = new ArrayList<>();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivitis, flows, engconf.getActivityFontName(),
                engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        OutputStream out = new FileOutputStream("D:\\temp\\flowable_images\\123.bmp");
        IOUtils.copy(in, out);
        System.out.println("结束");

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
