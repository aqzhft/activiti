package cc.powind.activiti.business.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class BaseService <T extends BaseEntity, K> {

    @Autowired
    private BaseRepository<T> repository;

    @Autowired
    protected RestTemplate restTemplate;

    @Transactional
    public T add(T request) {

        repository.save(request);

        postAdd(request);

        return request;
    }

    @Transactional
    public T edit(T request) {

        T entity = find(request.getId());
        Assert.notNull(entity, "未查询到指定的记录 ID -> " + request.getId());

        write(request, entity);

        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {

        T entity = find(id);
        Assert.notNull(entity, "未查询到指定的记录 ID -> " + id);

        entity.setDeleteFlag(false);

        repository.save(entity);
    }

    protected T find(Long id) {
        return id == null ? null : repository.findById(id).orElse(null);
    }

    protected List<T> find(Long[] ids) {
        return repository.findAllById(Arrays.asList(ids));
    }

    protected List<T> findAll() {
        return repository.findAll();
    }

    protected List<T> findAll(K request) {
        return repository.findAll(createSpecification(request));
    }

    protected abstract Specification<T> createSpecification(K request);

    protected void write(T source, T target) {}

    protected void postAdd(T entity) {}
}
