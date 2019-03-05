package net.treebear.kwifimanager.bean;

/**
 * @author Administrator
 * @date 2017/11/22
 */

public class WifiUserInfo {


    /**
     * id : 30
     * name : null
     * mobile : 13797470026
     * email : null
     * authStatus : 0
     * passwd : 3d3aa4baff65ac68c4c6e9365ed2d95f
     * avatar : null
     * inviteCode : null
     * inviteUser : null
     * sex : 0
     * apikey : null
     * role : 1
     * createTime : 1551256974950
     * updateTime : 1551347014277
     * isDelete : 0
     */

    private int id;
    private String token;
    private String name;
    private String mobile;
    private String email;
    private int authStatus;
    private String passwd;
    private String avatar;
    private int sex;
    private String apikey;
    private int role;
    private long createTime;
    private long updateTime;
    private int isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "WifiUserInfo{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", authStatus=" + authStatus +
                ", passwd='" + passwd + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", apikey='" + apikey + '\'' +
                ", role=" + role +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
