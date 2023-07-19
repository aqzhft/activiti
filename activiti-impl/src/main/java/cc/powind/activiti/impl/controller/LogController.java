package cc.powind.activiti.impl.controller;

import cc.powind.activiti.core.model.TaskLog;
import cc.powind.activiti.core.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService service;

    @GetMapping("")
    public List<TaskLog> findByInstanceId(String instanceId) {
        return service.findByInstanceId(instanceId);
    }
}
