package com.cjq.SpringBootDemo.bpm;

import com.cjq.SpringBootDemo.Application;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Stream;

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

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
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
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngineConfiguration.buildProcessEngine();
    }


    /**
     * 部署流程
     */
    @Test
    public void deployProcess(){
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("processes/holiday.bpmn");//bpmn文件的名称
        builder.deploy();
    }

    /**
     * 创建流程
     */
    @Test
    public void createProcess(){
        Map<String,Object> var = new HashMap<>();
        var.put("emp","cjq");
        var.put("days",5);
        var.put("manager","zhangsan");
        var.put("leader","lisi");
        var.put("hr","wangwu");
//        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("holiday", var);//流程的名称
        ProcessInstance holiday = runtimeService.startProcessInstanceById("holiday:1:17504", var);//流程定义ID
        System.out.println("Instance:"+holiday.getProcessInstanceId());
    }

    /**
     * 审批任务
     */
    @Test
    public void approveTask(){
        String taskId = "70004";
        taskService.setVariableLocal(taskId,"context","同意");
        taskService.complete(taskId);
    }

    /**
     * 查询待处理任务（待办）
     */
    @Test
    public void queryTask(){
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

}
