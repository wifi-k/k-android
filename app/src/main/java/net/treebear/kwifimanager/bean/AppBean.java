package net.treebear.kwifimanager.bean;

public class AppBean {

    private int id;

    private String iconUrl;

    private int iconRes;

    private String name;

    private long useTime;

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
