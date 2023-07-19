package cc.powind.activiti.business.service;

import cc.powind.activiti.business.data.HolidayEntity;
import cc.powind.activiti.business.data.HolidayEntityRepository;
import cc.powind.activiti.client.service.WorkflowService;
import cc.powind.activiti.core.model.UserTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class HolidayService implements WorkflowHandler {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HolidayEntityRepository repository;

    @Autowired
    private WorkflowService workflowService;

    public List<HolidayEntity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void add(HolidayEntity model) {

        model.setApplyTime(Instant.now());

        // 保存基础表单信息
        repository.save(model);
    }

    public HolidayEntity find(Long id) {
        return id == null ? null : repository.findById(id).orElse(null);
    }

    @Transactional
    public void submit(Long id) {

        // 就是将本地保存好的请假表单提交到工作流中，开启审批
        repository.findById(id).ifPresent(entity -> {

            entity.setSubmitted(true);
            repository.save(entity);

            workflowService.startProcessAndComplete("system_holiday", entity.getId() + "", entity.getApplyUserName() + "请假申请", entity.getApplyUserId(), "填写申请");
        });
    }

    @Override
    public void handle(String businessKey) {
        log.info("=====> system_holiday => businessKey: " + businessKey);

        // 填充数据
        HolidayEntity entity = find(NumberUtils.toLong(businessKey));
        Assert.notNull(entity, "未查询到指定的申请信息");

        List<UserTask> userTaskList = workflowService.findProcessKeyAndBusinessKey(supportProcessKey(), businessKey);
        if (userTaskList != null && !userTaskList.isEmpty()) {
            entity.setHolidayKey(userTaskList.get(0).getInstanceKey());
            repository.save(entity);
        } else {
            throw new IllegalArgumentException("没查到任务");
        }
    }

    @Override
    public String supportProcessKey() {
        return "system_holiday";
    }
}
