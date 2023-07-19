package cc.powind.activiti.impl.controller;

import cc.powind.activiti.core.model.Participant;
import cc.powind.activiti.core.model.TaskConfig;
import cc.powind.activiti.core.model.UserTask;
import cc.powind.activiti.core.service.UserTaskService;
import cc.powind.activiti.impl.bean.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/task")
public class UserTaskController {

    @Autowired
    private UserTaskService service;

    @GetMapping("/process")
    public List<UserTask> findByProcessId(String processId) {
        return service.findByProcessId(processId);
    }

    @GetMapping("/assignee")
    public List<UserTask> findAssigneeList(String userId) {
        return service.findAssignListByUserId(userId);
    }

    @GetMapping("/candidate")
    public List<UserTask> findCandidateList(String userId) {

        // 根据userId获取他的角色ID

        return service.findCandidateListByUserId(userId, Collections.singletonList("zhuguan"));
    }

    @PostMapping("/complete")
    public void complete(@RequestBody TaskRequest request) {
        service.complete(request.getProcessKey(), request.getBusinessKey(), request.getProcessDesc(), request.getApplyUserId(), request.getTaskName());
    }

    @PostMapping("/complete/{taskId}")
    public void complete(@PathVariable String taskId, String comment) {
        service.complete(taskId, comment);
    }

    @PostMapping("/claim")
    public void claim(String taskId, String userId) {
        service.claim(taskId, userId);
    }

    @DeleteMapping("/sendBack/{taskId}")
    public void sendBack(@PathVariable String taskId, String userId, String comment) {
        service.sendBack(taskId, userId, comment);
    }

    @PostMapping("/handover/{taskId}")
    public void handover(@PathVariable String taskId, String userId, String targetUserId, String comment) {
        service.handover(taskId, userId, targetUserId, comment);
    }

    @GetMapping("/candidate/{taskId}")
    public List<Participant> getCandidateList(@PathVariable String taskId) {
        return service.getCandidateList(taskId);
    }

    @PostMapping("/config/{taskId}")
    public void config(@PathVariable String taskId, @RequestBody TaskConfig config) {
        service.config(taskId, config);
    }

    @GetMapping("/config/{taskId}")
    public TaskConfig config(@PathVariable String taskId) {
        return service.findByTaskId(taskId);
    }

    @GetMapping("")
    public List<UserTask> findAll(@RequestParam String processKey, @RequestParam String businessKey) {
        return service.findAll(processKey, businessKey);
    }
}
