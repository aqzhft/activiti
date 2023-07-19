package cc.powind.activiti.impl.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskConfigRequest {

    private String processDefineKey;
}
