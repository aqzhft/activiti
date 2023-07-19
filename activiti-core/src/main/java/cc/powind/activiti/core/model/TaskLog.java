package cc.powind.activiti.core.model;

import java.time.Instant;

public class TaskLog {

    // 标识哪个任务
    private String id;

    // 干了什么
    private String name;

    // 什么时候开始的
    private Instant startTime;

    // 什么时候结束的
    private Instant endTime;

    // 执行人是谁
    private String executorId;

    // 评论
    private String[] comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }
}
