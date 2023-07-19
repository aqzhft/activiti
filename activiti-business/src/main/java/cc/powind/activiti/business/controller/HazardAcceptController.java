package cc.powind.activiti.business.controller;

import cc.powind.activiti.business.base.BaseController;
import cc.powind.activiti.business.bean.HazardAcceptRequest;
import cc.powind.activiti.business.data.HazardAcceptEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hazard/accept")
public class HazardAcceptController extends BaseController <HazardAcceptEntity, HazardAcceptRequest> {

}
