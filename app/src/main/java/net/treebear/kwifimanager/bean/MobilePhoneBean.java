package net.treebear.kwifimanager.bean;

import java.util.List;

/**
 * 手机设备
 * @author Administrator
 */
public class MobilePhoneBean {

    private long id;

    private String mac;

    private String ip;

    private String name;

    private String brand;
    /**
     * 水果手机，安卓手机，pad
     */
    private int type;
    /**
     * 在线、离线
     */
    private boolean online;
    /**
     * 上线时间
     */
    private long onlineTime;
    /**
     * 下线时间
     */
    private long offlineTime;
    /**
     * 上网时长rank
     */
    private int rank;
    /**
     * 平均上网时长
     */
    private long averageTime;
    /**
     * 上周活跃app
     */
    private List<AppBean> activeApp;

    public MobilePhoneBean() {
    }

    public MobilePhoneBean(String name, int type, boolean online, long onlineTime, long offlineTime, int rank, long averageTime, List<AppBean> activeApp) {
        this.name = name;
        this.type = type;
        this.online = online;
        this.onlineTime = onlineTime;
        this.offlineTime = offlineTime;
        this.rank = rank;
        this.averageTime = averageTime;
        this.activeApp = activeApp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public long getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(long offlineTime) {
        this.offlineTime = offlineTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<AppBean> getActiveApp() {
        return activeApp;
    }

    public void setActiveApp(List<AppBean> activeApp) {
        this.activeApp = activeApp;
    }

    public long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(long averageTime) {
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return "MobilePhoneBean{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + type +
                ", online=" + online +
                ", onlineTime=" + onlineTime +
                ", offlineTime=" + offlineTime +
                ", rank=" + rank +
                ", averageTime=" + averageTime +
                ", activeApp=" + activeApp +
                '}';
    }
}
