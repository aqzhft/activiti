package cc.powind.activiti.impl.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskConfigInfo {

    private String taskDefineId;

    private String taskName;

    public TaskConfigInfo(String taskDefineId, String taskName) {
        this.taskDefineId = taskDefineId;
        this.taskName = taskName;
    }
}
