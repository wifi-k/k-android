package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class TimeLimitBean implements Serializable, Parcelable {

    public static final Creator<TimeLimitBean> CREATOR = new Creator<TimeLimitBean>() {
        @Override
        public TimeLimitBean createFromParcel(Parcel source) {
            return new TimeLimitBean(source);
        }

        @Override
        public TimeLimitBean[] newArray(int size) {
            return new TimeLimitBean[size];
        }
    };
    private long id;
    private String name;
    private String startTime;
    private String endTime;
    private ArrayList<Daybean> days;

    public TimeLimitBean() {
    }

    public TimeLimitBean(String name, String startTime, String endTime, ArrayList<Daybean> days) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    protected TimeLimitBean(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.days = new ArrayList<Daybean>();
        in.readList(this.days, Daybean.class.getClassLoader());
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Daybean> getDays() {
        return days;
    }

    public void setDays(ArrayList<Daybean> days) {
        if (this.days == null) {
            this.days = days;
        } else {
            this.days.clear();
            this.days.addAll(days);
        }
    }

    @Override
    public String toString() {
        return "TimeLimitBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", days=" + days +
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
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeList(this.days);
    }
}
