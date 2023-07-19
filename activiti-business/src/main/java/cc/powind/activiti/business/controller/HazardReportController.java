package cc.powind.activiti.business.controller;

import cc.powind.activiti.business.base.BaseController;
import cc.powind.activiti.business.bean.HazardReportRequest;
import cc.powind.activiti.business.data.HazardReportEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hazard/report")
public class HazardReportController extends BaseController <HazardReportEntity, HazardReportRequest> {

}
