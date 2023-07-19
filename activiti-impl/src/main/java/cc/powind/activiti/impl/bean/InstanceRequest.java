package cc.powind.activiti.impl.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstanceRequest {

    private String applyUserId;

    private String processKey;

    private String businessKey;

    private String name;
}
