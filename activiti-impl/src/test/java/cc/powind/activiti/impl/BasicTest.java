package cc.powind.activiti.impl;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void deploy() {

        ClassPathResource classPathResource = new ClassPathResource("processes/111.bpmn");

        repositoryService.createDeployment()
                .addInputStream("demo", classPathResource)
                .key("holiday")
                .name("请假流程")
                .deploy();

    }

    @Test
    public void processDefinitionList() {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        System.out.println(list);
    }

    @Test
    public void taskNodeInfo() {
        BpmnModel bpmnModel = repositoryService.getBpmnModel("system_holiday:2:6");
        System.out.println(bpmnModel);

        // 获取有哪些任务
        List<UserTask> userTaskList = new ArrayList<>();

        List<Process> processes = bpmnModel.getProcesses();

        for (Process process : processes) {

            System.out.println("process_id: " + process.getId());

            for (FlowElement element : process.getFlowElements()) {

                if (element instanceof UserTask) {
                    userTaskList.add((UserTask) element);
                }
            }
        }

        // 打印出所有的任务
        System.out.println(userTaskList);
    }

    @Test
    public void startProcess() {

        Map<String, Object> param = new HashMap<>();
        param.put("employee", "lisi");

        // 设置启动流程的人
        Authentication.setAuthenticatedUserId("007");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("system_holiday", "2", param);

        runtimeService.setProcessInstanceName(processInstance.getProcessInstanceId(), "张三申请请假流程");

        System.out.println(processInstance);
    }

    @Test
    public void queryProcess() {
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("system_holiday")
                .list();
        System.out.println(processInstanceList);

        for (ProcessInstance instance : processInstanceList) {
            System.out.println(instance.getProcessVariables());
        }
    }


    @Test
    public void task() {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("system_holiday")
                //.taskCandidateGroup("lisi")
                .taskAssignee("lisi")
                .list();

        System.out.println(list);

        for (Task task : list) {
            System.out.println(task.getName());
        }
    }

    @Test
    public void completeTask() {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("system_holiday").list();
        for (Task task : list) {
            taskService.complete(task.getId());
        }
    }
}
