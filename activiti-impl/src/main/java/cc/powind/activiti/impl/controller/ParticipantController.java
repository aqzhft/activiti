package cc.powind.activiti.impl.controller;

import cc.powind.activiti.core.model.Participant;
import cc.powind.activiti.core.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService service;

    @GetMapping("")
    public List<Participant> findAll() {
        return service.findAll();
    }

    @GetMapping("/group")
    public List<String> groupIds() {
        return service.findAllGroupIds();
    }
}
