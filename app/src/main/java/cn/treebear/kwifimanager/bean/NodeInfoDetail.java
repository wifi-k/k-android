package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class NodeInfoDetail implements Serializable {
    /**
     * total : 11
     * page : [{"nodeId":"afad","name":"","firmware":"当前固件版本","manufactory":"设备制造商","model":"型号","status":"在线状态","bindTime":"绑定时间","isShare":1,"firmwareUpgrade":"固件升级版本号"},{}]
     */

    private int total;
    private List<NodeBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<NodeBean> getPage() {
        return page;
    }

    public void setPage(List<NodeBean> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "NodeInfoDetail{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }

    public static class NodeBean implements Serializable, Parcelable {

        public static final Creator<NodeBean> CREATOR = new Creator<NodeBean>() {
            @Override
            public NodeBean createFromParcel(Parcel source) {
                return new NodeBean(source);
            }

            @Override
            public NodeBean[] newArray(int size) {
                return new NodeBean[size];
            }
        };
        /**
         * nodeId : M1L201507221006635
         * manufactory : Treebear
         * model : Treebear-M1LV1
         * firmware : V0.5.3.2
         * bindTime : 1553845229073
         * isBind : 1
         * isShare : 0
         * comment : null
         * createTime : 1553226223009
         * updateTime : 1553847841545
         * isDelete : 0
         * partner : null
         * memory : 59
         * disk : 1
         * upstream : 0
         * downstream : 0
         * unbindTime : 1553844944543
         * shareTime : null
         * unshareTime : null
         * userId : 30
         * ip : null
         * name : xiaok-hiahia
         * inviteCode : null
         * status : 1
         * onlineTime : 1553839537692
         * offlineTime : 1553844601599
         * token : c7b767c2c80dbf75a094f4e8
         * health : 2
         * firmwareUpgrade : null
         * isSelect : 0
         */

        private String nodeId;
        private String manufactory;
        private String model;
        private String firmware;
        private long bindTime;
        private int isBind;
        private int isShare;
        private String comment;
        private long createTime;
        private long updateTime;
        private int isDelete;
        private String partner;
        private int memory;
        private int disk;
        private int upstream;
        private int downstream;
        private long unbindTime;
        private long shareTime;
        private long unshareTime;
        private int userId;
        private String ip;
        private String name;
        private String inviteCode;
        private int status;
        private long onlineTime;
        private long offlineTime;
        private String token;
        private int health;
        private String firmwareUpgrade;
        private int isSelect;

        public NodeBean() {
        }

        protected NodeBean(Parcel in) {
            this.nodeId = in.readString();
            this.manufactory = in.readString();
            this.model = in.readString();
            this.firmware = in.readString();
            this.bindTime = in.readLong();
            this.isBind = in.readInt();
            this.isShare = in.readInt();
            this.comment = in.readString();
            this.createTime = in.readLong();
            this.updateTime = in.readLong();
            this.isDelete = in.readInt();
            this.partner = in.readString();
            this.memory = in.readInt();
            this.disk = in.readInt();
            this.upstream = in.readInt();
            this.downstream = in.readInt();
            this.unbindTime = in.readLong();
            this.shareTime = in.readLong();
            this.unshareTime = in.readLong();
            this.userId = in.readInt();
            this.ip = in.readString();
            this.name = in.readString();
            this.inviteCode = in.readString();
            this.status = in.readInt();
            this.onlineTime = in.readLong();
            this.offlineTime = in.readLong();
            this.token = in.readString();
            this.health = in.readInt();
            this.firmwareUpgrade = in.readString();
            this.isSelect = in.readInt();
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getManufactory() {
            return manufactory;
        }

        public void setManufactory(String manufactory) {
            this.manufactory = manufactory;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getFirmware() {
            return firmware;
        }

        public void setFirmware(String firmware) {
            this.firmware = firmware;
        }

        public long getBindTime() {
            return bindTime;
        }

        public void setBindTime(long bindTime) {
            this.bindTime = bindTime;
        }

        public int getIsBind() {
            return isBind;
        }

        public void setIsBind(int isBind) {
            this.isBind = isBind;
        }

        public int getIsShare() {
            return isShare;
        }

        public void setIsShare(int isShare) {
            this.isShare = isShare;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public int getMemory() {
            return memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getDisk() {
            return disk;
        }

        public void setDisk(int disk) {
            this.disk = disk;
        }

        public int getUpstream() {
            return upstream;
        }

        public void setUpstream(int upstream) {
            this.upstream = upstream;
        }

        public int getDownstream() {
            return downstream;
        }

        public void setDownstream(int downstream) {
            this.downstream = downstream;
        }

        public long getUnbindTime() {
            return unbindTime;
        }

        public void setUnbindTime(long unbindTime) {
            this.unbindTime = unbindTime;
        }

        public long getShareTime() {
            return shareTime;
        }

        public void setShareTime(long shareTime) {
            this.shareTime = shareTime;
        }

        public long getUnshareTime() {
            return unshareTime;
        }

        public void setUnshareTime(long unshareTime) {
            this.unshareTime = unshareTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public String getFirmwareUpgrade() {
            return firmwareUpgrade;
        }

        public void setFirmwareUpgrade(String firmwareUpgrade) {
            this.firmwareUpgrade = firmwareUpgrade;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        @Override
        public String toString() {
            return "NodeBean{" +
                    "nodeId='" + nodeId + '\'' +
                    ", manufactory='" + manufactory + '\'' +
                    ", model='" + model + '\'' +
                    ", firmware='" + firmware + '\'' +
                    ", bindTime=" + bindTime +
                    ", isBind=" + isBind +
                    ", isShare=" + isShare +
                    ", comment='" + comment + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", isDelete=" + isDelete +
                    ", partner='" + partner + '\'' +
                    ", memory=" + memory +
                    ", disk=" + disk +
                    ", upstream=" + upstream +
                    ", downstream=" + downstream +
                    ", unbindTime=" + unbindTime +
                    ", shareTime=" + shareTime +
                    ", unshareTime=" + unshareTime +
                    ", userId=" + userId +
                    ", ip='" + ip + '\'' +
                    ", name='" + name + '\'' +
                    ", inviteCode='" + inviteCode + '\'' +
                    ", status=" + status +
                    ", onlineTime=" + onlineTime +
                    ", offlineTime=" + offlineTime +
                    ", token='" + token + '\'' +
                    ", health=" + health +
                    ", firmwareUpgrade='" + firmwareUpgrade + '\'' +
                    ", isSelect=" + isSelect +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nodeId);
            dest.writeString(this.manufactory);
            dest.writeString(this.model);
            dest.writeString(this.firmware);
            dest.writeLong(this.bindTime);
            dest.writeInt(this.isBind);
            dest.writeInt(this.isShare);
            dest.writeString(this.comment);
            dest.writeLong(this.createTime);
            dest.writeLong(this.updateTime);
            dest.writeInt(this.isDelete);
            dest.writeString(this.partner);
            dest.writeInt(this.memory);
            dest.writeInt(this.disk);
            dest.writeInt(this.upstream);
            dest.writeInt(this.downstream);
            dest.writeLong(this.unbindTime);
            dest.writeLong(this.shareTime);
            dest.writeLong(this.unshareTime);
            dest.writeInt(this.userId);
            dest.writeString(this.ip);
            dest.writeString(this.name);
            dest.writeString(this.inviteCode);
            dest.writeInt(this.status);
            dest.writeLong(this.onlineTime);
            dest.writeLong(this.offlineTime);
            dest.writeString(this.token);
            dest.writeInt(this.health);
            dest.writeString(this.firmwareUpgrade);
            dest.writeInt(this.isSelect);
        }
    }
}
