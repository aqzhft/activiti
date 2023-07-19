package cc.powind.activiti.business.controller;

import cc.powind.activiti.business.base.BaseController;
import cc.powind.activiti.business.bean.HazardAssessRequest;
import cc.powind.activiti.business.data.HazardAssessEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hazard/assess")
public class HazardAssessController extends BaseController <HazardAssessEntity, HazardAssessRequest> {

}
