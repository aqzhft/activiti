package cc.powind.activiti.impl.controller;

import cc.powind.activiti.core.model.Process;
import cc.powind.activiti.core.model.ProcessDefine;
import cc.powind.activiti.core.service.ProcessService;
import cc.powind.activiti.impl.bean.DeployRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService service;

    @GetMapping("/define")
    public List<ProcessDefine> findAllDefine() {
        return service.findAllRuntimeDefine();
    }

    @GetMapping("")
    public List<Process> findAll() {
        return service.findAllDetail();
    }

    @GetMapping("/xml/{processId}")
    public String getXml(@PathVariable String processId) {
        return service.getXml(processId);
    }

    @GetMapping("/xml/key/{key}")
    public String getXmlFromProcessKey(@PathVariable String key) {
        return service.getXmlFromProcessKey(key);
    }

    @PostMapping("/xml")
    public void xml(@RequestBody DeployRequest model) throws IOException {
        service.xml(model.getFileName(), model.getData().getBytes(StandardCharsets.UTF_8), model.getName(), model.getKey());
    }
}
