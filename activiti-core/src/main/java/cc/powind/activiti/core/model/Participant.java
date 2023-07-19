package cc.powind.activiti.core.model;

public class Participant {

    private String userId;

    private String userName;

    private String[] groupIds;

    public Participant(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Participant(String userId, String userName, String[] groupIds) {
        this.userId = userId;
        this.userName = userName;
        this.groupIds = groupIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }
}
