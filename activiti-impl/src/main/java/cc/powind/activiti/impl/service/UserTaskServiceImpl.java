package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.Process;
import cc.powind.activiti.core.model.*;
import cc.powind.activiti.core.service.ParticipantService;
import cc.powind.activiti.core.service.UserTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service("userTaskService")
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessServiceImpl processService;

    @Autowired
    private InstanceServiceImpl instanceService;

    @Autowired
    private ParticipantService participantService;

    /**
     * 查询指定流程下的所有任务【控制面板】
     */
    public List<UserTask> findByProcessId(String processId) {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionId(processId)
                .list();

        // 如果名称一样的只显示一个？

        List<UserTask> beanList = taskList.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());

        fill(beanList);

        return beanList;
    }

    /**
     * 查询指定人员的所有任务【我的任务】
     */
    public List<UserTask> findAssignListByUserId(String userId) {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();

        List<UserTask> beanList = taskList.stream().map(this::convert).collect(Collectors.toList());

        fill(beanList);

        return beanList;
    }

    /**
     * 查询指定人员的候选任务【任务广场】
     */
    public List<UserTask> findCandidateListByUserId(String userId, List<String> roleIds) {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userId, roleIds).list();
        List<UserTask> beanList = taskList.stream().map(this::convert).collect(Collectors.toList());
        fill(beanList);
        return beanList;
    }

    @Transactional
    public void complete(String processKey, String businessKey, String name, String userId, String taskName) {

        Instance instance = instanceService.add(processKey, businessKey, name, userId);

        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).taskName(taskName).singleResult();
        Assert.notNull(task, "未查询到名称为【" + taskName + "】的任务");

        taskService.complete(task.getId());
    }

    @Transactional
    public void complete(String taskId, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "COMPLETE", comment);
        taskService.complete(task.getId());
    }

    @Transactional
    public void claim(String taskId, String userId) {

        // todo 需要判断是否有资格获取这个任务

        taskService.claim(taskId, userId);
    }

    @Transactional
    public void sendBack(String taskId, String userId, String comment) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Assert.notNull(task, "当前任务不存在");

        // 如果人员不匹配
        if (task.getAssignee() == null) {
            throw new IllegalArgumentException("当前任务处于未领取的状态");
        }

        if (!task.getAssignee().equalsIgnoreCase(userId)) {
            throw new IllegalArgumentException("不能丢弃不属于自己的任务");
        }

        // 需要校验下是否有候选人
        List<Participant> candidateList = getCandidateList(taskId);
        if (candidateList.isEmpty()) {
            throw new IllegalArgumentException("没有其他人员能处理此任务，故禁止退回操作");
        }

        taskService.unclaim(taskId);

        taskService.addComment(taskId, task.getProcessInstanceId(), "SEND_BACK", comment);
    }

    @Transactional
    public void handover(String taskId, String userId, String targetUserId, String comment) {

        // 先丢弃
        sendBack(taskId, userId, comment);

        // 再获取
        claim(taskId, targetUserId);
    }

    /**
     * 获取一个任务的所有候选人
     * 查看候选人字段和分组字段是否能查询到人
     */
    public List<Participant> getCandidateList(String taskId) {

        List<IdentityLink> linkList = taskService.getIdentityLinksForTask(taskId);

        List<String> userIds = new ArrayList<>();
        List<String> groupIds = new ArrayList<>();

        for (IdentityLink link : linkList) {
            if ("candidate".equalsIgnoreCase(link.getType())) {

                if (link.getGroupId() != null) {
                    // 获取分组的人
                    groupIds.add(link.getGroupId());
                }

                if (link.getUserId() != null) {
                    userIds.add(link.getUserId());
                }
            }
        }

        List<Participant> resultList = new ArrayList<>();

        if (!userIds.isEmpty()) {
            resultList.addAll(participantService.findByUserIds(userIds.toArray(new String[0])));
        }

        if (!groupIds.isEmpty()) {
            resultList.addAll(participantService.findByGroupIds(groupIds.toArray(new String[0])));
        }

        return resultList;
    }

    @Transactional
    public void config(String taskId, TaskConfig config) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Assert.notNull(task, "未查询到指定的任务");

        if (StringUtils.isNotBlank(config.getAssignee())) {
            taskService.setAssignee(taskId, config.getAssignee());
        }

        if (config.getCandidates() != null) {
            for (String candidate : config.getCandidates()) {
                taskService.addCandidateUser(taskId, candidate);
            }
        }

        if (config.getGroupIds() != null) {
            for (String groupId : config.getGroupIds()) {
                taskService.addCandidateGroup(taskId, groupId);
            }
        }
    }

    public TaskConfig findByTaskId(String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Assert.notNull(task, "未查询到任务");

        List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);

        Set<String> candidates = new HashSet<>();
        Set<String> groupIds = new HashSet<>();
        for (IdentityLink link : identityLinkList) {

            if ("candidate".equalsIgnoreCase(link.getType()) && StringUtils.isNotBlank(link.getUserId())) {
                candidates.add(link.getUserId());
            }

            if (link.getGroupId() != null) {
                groupIds.add(link.getGroupId());
            }
        }

        TaskConfig config = new TaskConfig();
        config.setAssignee(task.getAssignee());
        config.setCandidates(candidates.toArray(new String[0]));
        config.setGroupIds(groupIds.toArray(new String[0]));

        return config;
    }

    @Override
    public List<UserTask> findAll(String processKey, String businessKey) {
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processKey).processInstanceBusinessKey(businessKey).list();
        List<UserTask> beanList = taskList.stream().map(this::convert).collect(Collectors.toList());
        fill(beanList);
        return beanList;
    }

    public UserTask convert(Task entity) {

        if (entity == null) {
            return null;
        }

        UserTask userTask = new UserTask();

        userTask.setId(entity.getId());
        userTask.setName(entity.getName());
        userTask.setBusinessKey(entity.getBusinessKey());
        userTask.setInstanceId(entity.getProcessInstanceId());
        userTask.setStartTime(entity.getCreateTime().toInstant());
        userTask.setExecutorId(entity.getAssignee());
        userTask.setProcessId(entity.getProcessDefinitionId());

        return userTask;
    }

    public void fill(List<UserTask> beanList) {

        if (beanList == null || beanList.isEmpty()) {
            return;
        }

        Map<String, String> processMapping = processService.findAll().stream().collect(Collectors.toMap(Process::getId, Process::getName));

        Map<String, Instance> instanceMapping = instanceService.findAll().stream().collect(Collectors.toMap(Instance::getId, bean -> bean));

        beanList.forEach(bean -> {

            if (bean.getProcessId() != null) {
                bean.setProcessName(processMapping.get(bean.getProcessId()));
            }

            if (bean.getInstanceId() != null) {
                Instance instance = instanceMapping.get(bean.getInstanceId());
                if (instance != null) {
                    bean.setInstanceKey(instance.getInstanceKey());
                    bean.setInstanceName(instance.getName());
                }
            }
        });
    }
}
