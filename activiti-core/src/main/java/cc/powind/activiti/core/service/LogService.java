package cc.powind.activiti.core.service;

import cc.powind.activiti.core.model.TaskLog;

import java.util.List;

public interface LogService {

    List<TaskLog> findByInstanceId(String instanceId);
}
