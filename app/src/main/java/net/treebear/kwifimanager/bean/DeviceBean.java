package net.treebear.kwifimanager.bean;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class DeviceBean implements Serializable {
    private String name;

    private String serialId;

    private boolean online;

    private String version;

    private String updateVersion;

    public DeviceBean(String name, String serialId, boolean online, String version, String updateVersion) {
        this.name = name;
        this.serialId = serialId;
        this.online = online;
        this.version = version;
        this.updateVersion = updateVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }
}
