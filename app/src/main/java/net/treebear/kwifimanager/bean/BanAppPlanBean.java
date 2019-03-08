package net.treebear.kwifimanager.bean;

import java.util.List;

/**
 * @author Administrator
 */
public class BanAppPlanBean {

    private long id;

    private String name;

    private List<TimeLimitBean> limitOnlineTime;

    private List<AppBean> banApps;

    private List<MobilePhoneBean> banMobile;

    public BanAppPlanBean(String name, List<TimeLimitBean> limitOnlineTime, List<AppBean> banApps, List<MobilePhoneBean> banMobile) {
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

    public List<TimeLimitBean> getLimitOnlineTime() {
        return limitOnlineTime;
    }

    public void setLimitOnlineTime(List<TimeLimitBean> limitOnlineTime) {
        this.limitOnlineTime = limitOnlineTime;
    }

    public List<AppBean> getBanApps() {
        return banApps;
    }

    public void setBanApps(List<AppBean> banApps) {
        this.banApps = banApps;
    }

    public List<MobilePhoneBean> getBanMobile() {
        return banMobile;
    }

    public void setBanMobile(List<MobilePhoneBean> banMobile) {
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
}
