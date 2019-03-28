package cn.treebear.kwifimanager.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.treebear.kwifimanager.config.ConstConfig;

public class Daybean implements Serializable, Parcelable {

    private String name;

    @ConstConfig.DayCode
    private int code;

    private boolean checked;

    public Daybean(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(@ConstConfig.DayCode int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Daybean{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", checked=" + checked +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.code);
    }

    protected Daybean(Parcel in) {
        this.name = in.readString();
        this.code = in.readInt();
    }

    public static final Creator<Daybean> CREATOR = new Creator<Daybean>() {
        @Override
        public Daybean createFromParcel(Parcel source) {
            return new Daybean(source);
        }

        @Override
        public Daybean[] newArray(int size) {
            return new Daybean[size];
        }
    };
}
