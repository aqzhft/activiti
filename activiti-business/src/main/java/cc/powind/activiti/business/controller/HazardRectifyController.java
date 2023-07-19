package cc.powind.activiti.business.controller;

import cc.powind.activiti.business.base.BaseController;
import cc.powind.activiti.business.bean.HazardRectifyRequest;
import cc.powind.activiti.business.data.HazardRectifyEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hazard/rectify")
public class HazardRectifyController extends BaseController <HazardRectifyEntity, HazardRectifyRequest> {

}
