package com.cjq.SpringBootDemo.bpm;

import com.cjq.SpringBootDemo.Application;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
/**
 * 问题：springboot整合websocket后运行"测试类"报错:
 * 解决办法：为SpringbootTest注解指定参数classes和webEnvironment
 * 原因：因为WebSocket是servlet容器所支持的，所以需要加载servlet容器：
 * webEnvironment参数为springboot指定ApplicationContext类型。
 * webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT表示内嵌的服务器将会在一个随机的端口启动。
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BpmTest {

    @Autowired
    ProcessEngine processEngine;

    /**
     * RepositoryService：提供一系列管理流程部署和流程定义的API。
     * RuntimeService：在流程运行时对流程实例进行管理与控制。
     * TaskService：对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
     * HistoryService：对流程的历史数据进行操作，包括查询、删除这些历史数据。
     * IdentityService：提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。
     * ManagementService：提供对流程引擎进行管理和维护的服务。
     * FormService：表单服务。
     */
    //管理流程定义
    private RepositoryService repositoryService;
    //创建，挂起，删除流程实例
    private RuntimeService runtimeService;
    //
    private TaskService taskService;
    private HistoryService historyService;

    @Before
    public void before(){
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
    }

    /**
     * 创建表
     */
    @Test
    public void createTables(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/springboot?useUnicode=true&amp;characterEncoding=utf8");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("root");
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        processEngineConfiguration.buildProcessEngine();
    }


    /**
     * 部署流程
     */
    @Test
    public void deployProcess(){
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("processes/test.bpmn");//bpmn文件的名称
        builder.deploy();
    }

    /**
     * 创建流程
     */
    @Test
    public void createProcessHoliday(){
        Map<String,Object> var = new HashMap<>();
        var.put("emp","cjq");
        var.put("days",5);
        var.put("manager","zhangsan");
        var.put("leader","lisi");
        var.put("hr","wangwu");
//        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("holiday", var);//流程的名称
        ProcessInstance holiday = runtimeService.startProcessInstanceById("holiday:1:4", var);//流程定义ID
        System.out.println("Instance:"+holiday.getProcessInstanceId());
    }

    /**
     * 创建流程
     */
    @Test
    public void createProcessPay(){
        Map<String,Object> var = new HashMap<>();
        var.put("emps","cjq,gyn,sy");
        var.put("manager","zhangsan");
        var.put("finance","wangwu");
        ProcessInstance holiday = runtimeService.startProcessInstanceById("pay:6:27511", var);//流程定义ID
        System.out.println("Instance:"+holiday.getProcessInstanceId());
    }

    /**
     * 创建流程
     */
    @Test
    public void createProcessTest(){
        Map<String,Object> var = new HashMap<>();
        var.put("emps","cjq,gyn,sy");
        var.put("manager","zhangsan");
        var.put("finance","wangwu");
        ProcessInstance holiday = runtimeService.startProcessInstanceById("myProcess_1:2:32514", var);//流程定义ID
        System.out.println("Instance:"+holiday.getProcessInstanceId());
    }

    /**
     * 签收任务
     */
    @Test
    public void claim(){
        String taskId = "30008";
        String userId = "gyn";
        taskService.claim(taskId,userId);
        //取消签收
//        taskService.unclaim();
    }

    /**
     * 审批任务
     */
    @Test
    public void approveTask(){
        String taskId = "37504";
        // 选通过taskId查询任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 从任务里拿到流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
        Authentication.setAuthenticatedUserId(task.getAssignee());//批注人的名称  一定要写，不然查看的时候不知道人物信息
        // 给任务添加批注
        taskService.addComment(taskId, processInstanceId, "同意");
        //审批
        taskService.complete(taskId);
    }

    /**
     * 查询任务备注
     */
    @Test
    public void queryTaskComment(){
        String taskId = "17507";
        List<Comment> taskComments = taskService.getTaskComments(taskId);
        for(Comment comment : taskComments){
            System.out.println(comment.getUserId()+":"+comment.getFullMessage());
        }
    }

    /**
     * 查询待处理任务（待办）
     */
    @Test
    public void queryTaskAssignee(){
        //根据assignee(处理人)查询任务
        String assignee = "cjq";
        List<Task> tasks = taskService.createTaskQuery()
                .active()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();
        for (Task task : tasks) {
            System.out.println("processInstId:" +task.getProcessInstanceId() +
                    ",taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }

    /**
     * 查看代签收的任务（多人）
     */
    @Test
    public void queryTaskCandidateUser(){
        //可签收的人
        String canDoUser = "gyn";
        //查询未签收的
        List<Task> tasks = taskService.createTaskQuery()
                .active()
                .taskCandidateUser(canDoUser)
                .orderByTaskCreateTime().desc()
                .list();
        for (Task task : tasks) {
            System.out.println("processInstId:" +task.getProcessInstanceId() +
                    ",taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }

    /**
     * 查看代签收的任务（小组）
     */
    @Test
    public void queryTasktaskCandidateGroup(){
        //可签收的人
        String canDoUser = "gyn";
        //查询未签收的
        List<Task> tasks = taskService.createTaskQuery()
                .active()
                .taskCandidateGroup(canDoUser)
                .orderByTaskCreateTime().desc()
                .list();
        for (Task task : tasks) {
            System.out.println("processInstId:" +task.getProcessInstanceId() +
                    ",taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }

    /**
     * 查询历史任务 (已办)
     */
    @Test
    public void queryHistory(){
        String assignee = "cjq";
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .finished()
                .taskAssignee(assignee)
                .orderByProcessDefinitionId().asc()
                .orderByHistoricTaskInstanceEndTime().asc()
                .list();
        System.out.println("完成的任务-------");
        for(HistoricTaskInstance task : list){
            System.out.println("processInstId:" +task.getProcessInstanceId() +
                    ",taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }

    /**
     * 查询流程参数
     */
    @Test
    public void queryVars(){
        String processInstId = "7501";
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstId).list();
        for(HistoricVariableInstance var : list){
            System.out.println(var.getVariableName() +" : "+var.getValue().toString());
        }
    }


    /**
     * 查询流程参数
     */
    @Test
    public void query(){
    }
}
