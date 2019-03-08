package net.treebear.kwifimanager.bean;

import java.util.List;

/**
 * @author Administrator
 */
public class TimeLimitBean {

    private long id;

    private String name;

    private String startTime;

    private String endTime;

    private List<Daybean> days;

    public TimeLimitBean(String name, String startTime, String endTime, List<Daybean> days) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
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

    public List<Daybean> getDays() {
        return days;
    }

    public void setDays(List<Daybean> days) {
        this.days = days;
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
}
