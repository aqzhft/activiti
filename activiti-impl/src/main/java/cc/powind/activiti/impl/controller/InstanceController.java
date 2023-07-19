package cc.powind.activiti.impl.controller;

import cc.powind.activiti.core.model.Instance;
import cc.powind.activiti.core.service.InstanceService;
import cc.powind.activiti.impl.bean.InstanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private InstanceService service;

    @PostMapping("")
    public void add(@RequestBody InstanceRequest model) {
        service.add(model.getProcessKey(), model.getBusinessKey(), model.getName(), model.getApplyUserId());
    }

    @GetMapping("")
    public List<Instance> findAll(String processId) {
        return service.findByProcessId(processId);
    }

    @GetMapping("/key")
    public List<Instance> findByProcessKey(String processKey) {
        return service.findByProcessKey(processKey);
    }

    @GetMapping("/historic")
    public List<Instance> findHistoric(String processId) {
        return service.findHistoricByProcessId(processId);
    }

    @GetMapping("/historic/key")
    public List<Instance> findHistoricByProcessKey(String processKey) {
        return service.findHistoricByProcessKey(processKey);
    }

    @PostMapping("/suspend/{instanceId}")
    public void suspend(@PathVariable String instanceId) {
        service.suspend(instanceId);
    }

    @DeleteMapping("/suspend/{instanceId}")
    public void discardSuspend(@PathVariable String instanceId) {
        service.discardSuspend(instanceId);
    }
}
