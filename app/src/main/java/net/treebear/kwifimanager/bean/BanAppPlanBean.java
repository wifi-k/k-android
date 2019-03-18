package net.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class BanAppPlanBean implements Serializable, Parcelable {

    private long id;

    private String name;

    private ArrayList<TimeLimitBean> limitOnlineTime;

    private ArrayList<AppBean> banApps;

    private ArrayList<MobilePhoneBean> banMobile;

    public BanAppPlanBean(String name, ArrayList<TimeLimitBean> limitOnlineTime, ArrayList<AppBean> banApps, ArrayList<MobilePhoneBean> banMobile) {
        this.name = name;
        this.limitOnlineTime = limitOnlineTime;
        this.banApps = banApps;
        this.banMobile = banMobile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TimeLimitBean> getLimitOnlineTime() {
        return limitOnlineTime;
    }

    public void setLimitOnlineTime(ArrayList<TimeLimitBean> limitOnlineTime) {
        this.limitOnlineTime = limitOnlineTime;
    }

    public ArrayList<AppBean> getBanApps() {
        return banApps;
    }

    public void setBanApps(ArrayList<AppBean> banApps) {
        if (this.banApps == null) {
            this.banApps = banApps;
        } else {
            this.banApps.clear();
            this.banApps.addAll(banApps);
        }
    }

    public ArrayList<MobilePhoneBean> getBanMobile() {
        return banMobile;
    }

    public void setBanMobile(ArrayList<MobilePhoneBean> banMobile) {
        this.banMobile = banMobile;
    }

    @Override
    public String toString() {
        return "BanAppPlanBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", limitOnlineTime=" + limitOnlineTime +
                ", banApps=" + banApps +
                ", banMobile=" + banMobile +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeList(this.limitOnlineTime);
        dest.writeList(this.banApps);
        dest.writeList(this.banMobile);
    }

    protected BanAppPlanBean(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.limitOnlineTime = new ArrayList<TimeLimitBean>();
        in.readList(this.limitOnlineTime, TimeLimitBean.class.getClassLoader());
        this.banApps = new ArrayList<AppBean>();
        in.readList(this.banApps, AppBean.class.getClassLoader());
        this.banMobile = new ArrayList<MobilePhoneBean>();
        in.readList(this.banMobile, MobilePhoneBean.class.getClassLoader());
    }

    public static final Creator<BanAppPlanBean> CREATOR = new Creator<BanAppPlanBean>() {
        @Override
        public BanAppPlanBean createFromParcel(Parcel source) {
            return new BanAppPlanBean(source);
        }

        @Override
        public BanAppPlanBean[] newArray(int size) {
            return new BanAppPlanBean[size];
        }
    };
}
