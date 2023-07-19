package cc.powind.activiti.business.service;

import cc.powind.activiti.business.base.BaseService;
import cc.powind.activiti.business.bean.HazardReportRequest;
import cc.powind.activiti.business.data.HazardReportEntity;
import cc.powind.activiti.business.data.HazardReportEntityRepository;
import cc.powind.activiti.client.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HazardReportService extends BaseService<HazardReportEntity, HazardReportRequest> implements WorkflowHandler {

    @Autowired
    private HazardReportEntityRepository repository;

    @Autowired
    private WorkflowService workflowService;

    @Override
    protected Specification<HazardReportEntity> createSpecification(HazardReportRequest request) {

        return  (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (StringUtils.isNotBlank(request.getType())) {
                predicateList.add(criteriaBuilder.equal(root.get("type"), request.getType()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    @Override
    protected void postAdd(HazardReportEntity entity) {

        // 开启工作流 并完成提交工作
        workflowService.startProcessAndComplete("system_hidden_hazard", entity.getId() + "", "隐患排查：" + entity.getName(), entity.getReporterId() + "", "上报");
    }

    @Override
    public void handle(String businessKey) {
        log.info("=====> system_hidden_hazard => businessKey: " + businessKey);
    }

    @Override
    public String supportProcessKey() {
        return "system_hidden_hazard";
    }
}
