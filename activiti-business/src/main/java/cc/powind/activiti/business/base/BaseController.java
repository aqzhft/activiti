package cc.powind.activiti.business.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController <T extends BaseEntity, K> {

    @Autowired
    private BaseService<T, K> service;

    @PostMapping("")
    public T add(@RequestBody T request) {
        return service.add(request);
    }

    @PutMapping("")
    public T edit(@RequestBody T request) {
        return service.edit(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("")
    public List<T> findAll() {
        return service.findAll();
    }
}
