package cc.powind.activiti.core.model;

import java.util.ArrayList;
import java.util.List;

public class Process {

    // 流程的ID
    private String id;

    // 编号
    private String key;

    // 名称
    private String name;

    // 版本号
    private Integer version;

    // 实例列表
    private List<Instance> instanceList = new ArrayList<>();

    // 配置任务列表
    private List<UserTask> taskList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Instance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    public List<UserTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<UserTask> taskList) {
        this.taskList = taskList;
    }
}
