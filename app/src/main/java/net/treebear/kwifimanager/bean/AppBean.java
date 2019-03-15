package net.treebear.kwifimanager.bean;

import java.io.Serializable;

public class AppBean implements Serializable {

    private int id;

    private String iconUrl;

    private int iconRes;

    private String name;

    private long useTime;

    private boolean ban;

    public AppBean(String name, int iconRes,boolean ban) {
        this.iconRes = iconRes;
        this.name = name;
        this.ban = ban;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "id=" + id +
                ", iconUrl='" + iconUrl + '\'' +
                ", iconRes=" + iconRes +
                ", name='" + name + '\'' +
                ", useTime=" + useTime +
                '}';
    }
}
