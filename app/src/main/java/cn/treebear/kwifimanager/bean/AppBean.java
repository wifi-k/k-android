package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AppBean implements Serializable, Parcelable {

    public static final Creator<AppBean> CREATOR = new Creator<AppBean>() {
        @Override
        public AppBean createFromParcel(Parcel source) {
            return new AppBean(source);
        }

        @Override
        public AppBean[] newArray(int size) {
            return new AppBean[size];
        }
    };
    private int id;
    private String iconUrl;
    private int iconRes;
    private String name;
    private long useTime;
    private boolean ban;

    public AppBean(String name, int iconRes, boolean ban) {
        this.iconRes = iconRes;
        this.name = name;
        this.ban = ban;
    }

    protected AppBean(Parcel in) {
        this.id = in.readInt();
        this.iconUrl = in.readString();
        this.iconRes = in.readInt();
        this.name = in.readString();
        this.useTime = in.readLong();
        this.ban = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.iconUrl);
        dest.writeInt(this.iconRes);
        dest.writeString(this.name);
        dest.writeLong(this.useTime);
        dest.writeByte(this.ban ? (byte) 1 : (byte) 0);
    }
}
