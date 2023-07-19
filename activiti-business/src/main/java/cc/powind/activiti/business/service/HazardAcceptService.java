package cc.powind.activiti.business.service;

import cc.powind.activiti.business.base.BaseService;
import cc.powind.activiti.business.bean.HazardAcceptRequest;
import cc.powind.activiti.business.data.HazardAcceptEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HazardAcceptService extends BaseService<HazardAcceptEntity, HazardAcceptRequest> {

    @Override
    protected Specification<HazardAcceptEntity> createSpecification(HazardAcceptRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();



            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
