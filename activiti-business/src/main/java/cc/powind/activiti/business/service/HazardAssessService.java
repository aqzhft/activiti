package cc.powind.activiti.business.service;

import cc.powind.activiti.business.base.BaseService;
import cc.powind.activiti.business.bean.HazardAssessRequest;
import cc.powind.activiti.business.data.HazardAssessEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HazardAssessService extends BaseService<HazardAssessEntity, HazardAssessRequest> {

    @Override
    protected Specification<HazardAssessEntity> createSpecification(HazardAssessRequest request) {

        return  (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();



            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
