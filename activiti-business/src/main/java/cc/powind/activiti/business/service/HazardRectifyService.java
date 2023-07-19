package cc.powind.activiti.business.service;

import cc.powind.activiti.business.base.BaseService;
import cc.powind.activiti.business.bean.HazardRectifyRequest;
import cc.powind.activiti.business.data.HazardRectifyEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HazardRectifyService extends BaseService<HazardRectifyEntity, HazardRectifyRequest> {

    @Override
    protected Specification<HazardRectifyEntity> createSpecification(HazardRectifyRequest request) {

        return  (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
