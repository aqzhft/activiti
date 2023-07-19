package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.Instance;
import cc.powind.activiti.core.service.InstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("instanceService")
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Transactional
    public Instance add(String processKey, String businessKey, String name, String applyUserId) {
        Authentication.setAuthenticatedUserId(applyUserId);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, businessKey);
        runtimeService.setProcessInstanceName(instance.getProcessInstanceId(), name);
        return convert(instance);
    }

    public List<Instance> findByProcessId(String processId) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().processDefinitionId(processId).list();
        return instanceList.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<Instance> findByProcessKey(String processKey) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).list();
        return instanceList.stream().map(this::convert).sorted((o1, o2) -> o2.getApplyTime().compareTo(o1.getApplyTime())).collect(Collectors.toList());
    }

    public List<Instance> findByProcessId(String[] processIds) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().processDefinitionIds(Arrays.stream(processIds).collect(Collectors.toSet())).list();
        return instanceList.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<Instance> findHistoricByProcessId(String processId) {
        List<HistoricProcessInstance> instanceList = historyService.createHistoricProcessInstanceQuery().processDefinitionId(processId).finished().list();
        return instanceList.stream().map(this::convert).collect(Collectors.toList());
    }

    public List<Instance> findHistoricByProcessKey(String processKey) {
        List<HistoricProcessInstance> instanceList = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processKey).finished().list();
        return instanceList.stream().map(this::convert).sorted((o1, o2) -> o2.getApplyTime().compareTo(o1.getApplyTime())).collect(Collectors.toList());
    }

    public List<Instance> findAll() {
        List<ProcessInstance> entities = runtimeService.createProcessInstanceQuery().list();
        return entities.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void suspend(String instanceId) {
        runtimeService.suspendProcessInstanceById(instanceId);
    }

    public void discardSuspend(String instanceId) {
        runtimeService.activateProcessInstanceById(instanceId);
    }

    @Override
    public void delete(String processKey, String businessKey, String reason) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).processInstanceBusinessKey(businessKey).singleResult();
        if (instance != null) {
            runtimeService.deleteProcessInstance(instance.getProcessInstanceId(), reason);
        }
    }

    private Instance convert(ProcessInstance entity) {

        if (entity == null) {
            return null;
        }

        Instance instance = new Instance();

        instance.setId(entity.getId());
        instance.setProcessId(entity.getProcessDefinitionId());
        instance.setProcessKey(entity.getProcessDefinitionKey());
        instance.setProcessVersion(entity.getProcessDefinitionVersion());
        instance.setBusinessKey(entity.getBusinessKey());
        instance.setName(entity.getName());
        instance.setApplyTime(entity.getStartTime().toInstant());
        instance.setApplyUserId(entity.getStartUserId());
        instance.setLocked(entity.isSuspended());
        instance.setFinishTime(null);

        return instance;
    }

    private Instance convert(HistoricProcessInstance entity) {

        if (entity == null) {
            return null;
        }

        Instance instance = new Instance();

        instance.setId(entity.getId());
        instance.setProcessId(entity.getProcessDefinitionId());
        instance.setProcessKey(entity.getProcessDefinitionKey());
        instance.setProcessVersion(entity.getProcessDefinitionVersion());
        instance.setBusinessKey(entity.getBusinessKey());
        instance.setName(entity.getName());
        instance.setApplyTime(entity.getStartTime().toInstant());
        instance.setApplyUserId(entity.getStartUserId());
        instance.setLocked(false);
        instance.setFinishTime(entity.getEndTime() == null ? null : entity.getEndTime().toInstant());

        return instance;
    }
}
