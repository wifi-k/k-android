package net.treebear.kwifimanager.bean;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * @author Administrator
 * @date 2017/11/22
 */

public class UserInfoBean {

    private String token;

    private String phone;

    /**
     * 用户头像路径
     */
    private String avatar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
