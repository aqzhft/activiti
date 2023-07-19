package cc.powind.activiti.core.service;

import cc.powind.activiti.core.model.Instance;

import java.util.List;

public interface InstanceService {

    Instance add(String processKey, String businessKey, String name, String applyUserId);

    List<Instance> findByProcessId(String processId);

    List<Instance> findByProcessKey(String processKey);

    List<Instance> findByProcessId(String[] processIds);

    List<Instance> findHistoricByProcessId(String processId);

    List<Instance> findHistoricByProcessKey(String processKey);

    List<Instance> findAll();

    void suspend(String instanceId);

    void discardSuspend(String instanceId);

    void delete(String processKey, String businessKey, String reason);
}
