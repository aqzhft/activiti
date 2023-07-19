package cc.powind.activiti.core.service;

import cc.powind.activiti.core.model.Participant;
import cc.powind.activiti.core.model.TaskConfig;
import cc.powind.activiti.core.model.UserTask;

import java.util.List;

public interface UserTaskService {

    /**
     * 查询指定流程下的所有任务【控制面板】
     */
    List<UserTask> findByProcessId(String processId);

    /**
     * 查询指定人员的所有任务【我的任务】
     */
    List<UserTask> findAssignListByUserId(String userId);

    /**
     * 查询指定人员的候选任务【任务广场】
     */
    List<UserTask> findCandidateListByUserId(String userId, List<String> roleIds);

    void complete(String processKey, String businessKey, String name, String userId, String taskName);

    void complete(String taskId, String comment);

    void claim(String taskId, String userId);

    void sendBack(String taskId, String userId, String comment);

    void handover(String taskId, String userId, String targetUserId, String comment);

    /**
     * 获取一个任务的所有候选人
     * 查看候选人字段和分组字段是否能查询到人
     */
    List<Participant> getCandidateList(String taskId);

    void config(String taskId, TaskConfig config);

    TaskConfig findByTaskId(String taskId);

    List<UserTask> findAll(String processKey, String businessKey);
}
