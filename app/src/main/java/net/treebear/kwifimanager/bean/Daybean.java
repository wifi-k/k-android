package net.treebear.kwifimanager.bean;


import net.treebear.kwifimanager.config.ConstConfig;

public class Daybean {

    private String name;

    @ConstConfig.DayCode
    private int code;

    public Daybean(String name, int code) {
        this.name = name;
        this.code = code;
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
}
