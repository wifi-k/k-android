package cn.treebear.kwifimanager.bean;

public class FamilyMemberBean {
    /**
     * userId : 11
     * userName :
     * userMobile :
     * userAvatar :
     * nodeId :
     * nodeName :
     * role : 0
     */

    private int userId;
    private String userName;
    private String userMobile;
    private String userAvatar;
    private String nodeId;
    private String nodeName;
    private int role;

    public FamilyMemberBean() {
    }

    public FamilyMemberBean(String userName, String userMobile) {
        this.userName = userName;
        this.userMobile = userMobile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "FamilyMemberBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", role=" + role +
                '}';
    }
}