package cc.powind.activiti.impl.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    private String processKey;

    private String businessKey;

    private String processDesc;

    private String taskName;

    private String applyUserId;
}
