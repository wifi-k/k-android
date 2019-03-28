package cn.treebear.kwifimanager.util;

import com.google.gson.Gson;

import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.bean.ServerUserInfo;

/**
 * Created by Administrator on 2017/11/22.
 * 管理用户信息
 */

public class UserInfoUtil {

    static ServerUserInfo mUserInfo;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static ServerUserInfo getUserInfo() {
        synchronized (UserInfoUtil.class) {
            if (mUserInfo == null) {
                Gson gson = new Gson();
                String json = (String) SharedPreferencesUtil.getParam(MyApplication.getAppContext(), SharedPreferencesUtil.USER_INFO, "{}");
                TLog.w("OkHttp-json", json);
                try {
                    mUserInfo = gson.fromJson(json, ServerUserInfo.class);
                } catch (Exception e) {
                    mUserInfo = new ServerUserInfo();
                }
                if (mUserInfo == null) {
                    mUserInfo = new ServerUserInfo();
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
    public static void updateUserInfo(ServerUserInfo info) {
        synchronized (UserInfoUtil.class) {
            Gson gson = new Gson();
            String json = gson.toJson(info);
            TLog.w("OkHttp", json);
            SharedPreferencesUtil.setParam(MyApplication.getAppContext(), SharedPreferencesUtil.USER_INFO, json);
            mUserInfo = info;
        }
    }

    public static void clearUserInfo() {
        TLog.w("OkHttp - clearUserInfo");
        updateUserInfo(new ServerUserInfo());
    }

    public static boolean isLogin() {
        return Check.hasContent(getUserInfo().getToken());
    }

}
