package cc.powind.activiti.business.controller;

import cc.powind.activiti.business.data.HolidayEntity;
import cc.powind.activiti.business.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService service;

    @GetMapping("")
    public List<HolidayEntity> findAll() {
        return service.findAll();
    }

    @PostMapping("")
    public void add(@RequestBody HolidayEntity model) {
        service.add(model);
    }

    @PostMapping("/process/{id}")
    public void submit(@PathVariable Long id) {
        service.submit(id);
    }
}
