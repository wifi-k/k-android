package net.treebear.kwifimanager.bean;

/**
 * @author Administrator
 * @date 2017/11/22
 */

public class UserInfoBean {
    /**
     * 用户姓名
     */
    private String name;
    /**
     * APi-token
     */
    private String token;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户认证状态 :0-未认证 1-认证通过
     */
    private int authStatus;
    /**
     * 用户头像路径
     */
    private String avatar;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", authStatus=" + authStatus +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
