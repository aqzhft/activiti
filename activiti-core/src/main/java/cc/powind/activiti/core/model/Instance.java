package cc.powind.activiti.core.model;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Instance {

    private String id;

    private String businessKey;

    private String name;

    private String processId;

    private String processKey;

    private Integer processVersion;

    private Instant applyTime;

    private String applyUserId;

    private Boolean locked;

    private Instant finishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public Integer getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(Integer processVersion) {
        this.processVersion = processVersion;
    }

    public Instant getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Instant applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public Long getDurationSeconds() {

        if (applyTime != null && finishTime == null) {
            return Instant.now().getEpochSecond() - applyTime.getEpochSecond();
        }

        else if (applyTime != null) {
            return finishTime.getEpochSecond() - applyTime.getEpochSecond();
        }

        else {
            return null;
        }
    }

    public String getInstanceKey() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return ProcessKeyMappings.getAliasName(processKey) + "-" + applyTime.atZone(ZoneId.systemDefault()).format(formatter) + "-" + StringUtils.leftPad(String.valueOf(processVersion), 3, "0") + "-" + StringUtils.leftPad(id, 6, "0");
    }
}
