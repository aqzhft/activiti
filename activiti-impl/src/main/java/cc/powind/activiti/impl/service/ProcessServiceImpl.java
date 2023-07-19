package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.Instance;
import cc.powind.activiti.core.model.Process;
import cc.powind.activiti.core.model.ProcessDefine;
import cc.powind.activiti.core.service.ProcessService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private InstanceServiceImpl instanceService;

    public List<ProcessDefine> findAllRuntimeDefine() {
        List<Process> processList = findAllDetail();
        Map<String, List<Process>> collect = processList.stream().collect(Collectors.groupingBy(Process::getKey));

        List<ProcessDefine> defineList = new ArrayList<>();
        for (String key : collect.keySet()) {

            ProcessDefine definition = new ProcessDefine();
            definition.setKey(key);
            definition.setProcessList(collect.getOrDefault(key, Collections.emptyList()));
            definition.setName(definition.getProcessList().isEmpty() ? "" : definition.getProcessList().get(0).getName());

            defineList.add(definition);
        }

        return defineList;
    }

    public List<Process> findAllDetail() {

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();

        String[] processIds = processDefinitionList.stream().map(ProcessDefinition::getId).toArray(String[]::new);

        Map<String, List<Instance>> collect = instanceService.findByProcessId(processIds).stream().collect(Collectors.groupingBy(Instance::getProcessId));

        List<Process> processList = processDefinitionList.stream().map(this::convert).collect(Collectors.toList());

        for (Process process : processList) {
            process.setInstanceList(collect.get(process.getId()));
        }

        return processList;
    }

    public Process findByProcessId(String processId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processId).singleResult();
        return convert(definition);
    }

    public List<Process> findAll() {
        List<ProcessDefinition> entities = repositoryService.createProcessDefinitionQuery().list();
        return entities.stream().map(this::convert).collect(Collectors.toList());
    }

    public String getXml(String processId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        byte[] bytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public String getXmlFromProcessKey(String processKey) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion().singleResult();
        if (definition != null) {
            return getXml(definition.getId());
        }
        return null;
    }

    public void xml(String fileName, byte[] bytes, String processName, String processKey) {
        repositoryService.createDeployment().name(processName).key(processKey).addBytes(fileName, bytes).deploy();
    }

    private Process convert(ProcessDefinition entity) {

        if (entity == null) {
            return null;
        }

        Process bean = new Process();
        bean.setId(entity.getId());
        bean.setKey(entity.getKey());
        bean.setName(entity.getName());
        bean.setVersion(entity.getVersion());

        return bean;
    }
}
