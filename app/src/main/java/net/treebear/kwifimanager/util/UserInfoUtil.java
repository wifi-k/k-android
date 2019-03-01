package net.treebear.kwifimanager.util;

import com.google.gson.Gson;

import net.treebear.kwifimanager.MyApplication;
import net.treebear.kwifimanager.bean.UserInfoBean;

/**
 * Created by Administrator on 2017/11/22.
 * 管理用户信息
 */

public class UserInfoUtil {

    static UserInfoBean mUserInfo;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static UserInfoBean getUserInfo() {
        synchronized (UserInfoUtil.class) {
            if (mUserInfo == null) {
                Gson gson = new Gson();
                String json = (String) SharedPreferencesUtil.getParam(MyApplication.getAppContext(), SharedPreferencesUtil.USER_INFO, "");
                try {
                    mUserInfo = gson.fromJson(json, UserInfoBean.class);
                } catch (Exception e) {
                    mUserInfo = new UserInfoBean();
                }
                if (mUserInfo == null) {
                    mUserInfo = new UserInfoBean();
                }
            }
        }
        return mUserInfo;
    }

    /**
     * 更新用户信息
     *
     * @param info info
     */
    public static void updateUserInfo(UserInfoBean info) {
        synchronized (UserInfoUtil.class) {
            Gson gson = new Gson();
            String json = gson.toJson(info);
            SharedPreferencesUtil.setParam(MyApplication.getAppContext(), SharedPreferencesUtil.USER_INFO, json);
            mUserInfo = info;
        }
    }

    public static boolean isLogin() {
        return Check.hasContent(getUserInfo().getToken());
    }

}
