package cc.powind.activiti.core.model;

/**
 * 任务配置
 */
public class TaskConfig {

    /**
     * 执行人
     */
    private String assignee;

    /**
     * 候选人
     */
    private String[] candidates;

    /**
     * 候选人分组
     */
    private String[] groupIds;

    /**
     * 超时的小时数
     */
    private Integer timeout;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String[] getCandidates() {
        return candidates;
    }

    public void setCandidates(String[] candidates) {
        this.candidates = candidates;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
