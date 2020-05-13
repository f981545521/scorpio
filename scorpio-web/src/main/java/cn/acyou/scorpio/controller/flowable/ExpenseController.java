package cn.acyou.scorpio.controller.flowable;

import cn.acyou.framework.model.Result;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报销demoController
 *
 * @author puhaiyang
 * @date 2018/12/19
 */
@Api(tags = "工作流测试")
@Controller
@RequestMapping(value = "expense")
public class ExpenseController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 添加报销
     *
     * @param userId 用户Id
     * @param money  报销金额
     */
    @GetMapping(value = "add")
    @ResponseBody
    public Result addExpense(String userId, Integer money) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return Result.success("提交成功.流程Id为：" + processInstance.getId());
    }

    /**
     * 获取审批管理列表
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public Result list(String userId) {
        List<Map<String, String>> resultList = Lists.newArrayList();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            Map<String, String> taskMap = new HashMap<>();
            taskMap.put("taskId", task.getId());
            taskMap.put("taskName", task.getName());
            resultList.add(taskMap);
        }
        return Result.success(resultList);
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @GetMapping(value = "apply")
    @ResponseBody
    public Result apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return Result.success("批准");
    }

    /**
     * 拒绝
     */
    @GetMapping(value = "reject")
    @ResponseBody
    public Result reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return Result.success("驳回");
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @GetMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        System.out.println("流程图绘制成功");
        IOUtils.copy(in, httpServletResponse.getOutputStream());
    }
}
