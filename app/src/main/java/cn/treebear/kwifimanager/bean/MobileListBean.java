package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class MobileListBean implements Serializable {


    /**
     * total : 2
     * page : [{"nodeId":"","mac":"","name":"主机名","onTime":"最近在线时间戳","offTime":"最近离线时间戳","note":"设备备注名","status":0,"isBlock":0},{}]
     */

    private int total;
    private List<MobileBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MobileBean> getPage() {
        return page;
    }

    public void setPage(List<MobileBean> page) {
        this.page = page;
    }

    public static class MobileBean implements Serializable, Parcelable {

        /**
         * mac : 8c:25:05:bf:9f:57
         * name : Honor_8-4b0f822bd35bf1af
         * onTime : 1554199524
         * offTime : 0
         * status : 1
         * note : null
         * nodeId : 214216BF0F3D
         * createTime : 1554199525220
         * updateTime : 1554199525220
         * isDelete : 0
         * isBlock : 0
         * macVendor : huawei
         * macIcon : http://test.developer.qiniu.famwifi.com/ic_hw.png?e=1554200145&token=yl1JVmF9Vx6HuZ1dapbIEbZ4lb__T0Z71tXZ71zj:ce1wHNQWUHErKMYUwMJYNdZOY2Q=
         */

        private String mac;
        private String name;
        private int onTime;
        private int offTime;
        private int status;
        private String note;
        private String nodeId;
        private long createTime;
        private long updateTime;
        private int isDelete;
        private int isBlock;
        private String macVendor;
        private String macIcon;
        String ip;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOnTime() {
            return onTime;
        }

        public void setOnTime(int onTime) {
            this.onTime = onTime;
        }

        public int getOffTime() {
            return offTime;
        }

        public void setOffTime(int offTime) {
            this.offTime = offTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
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

        public int getIsBlock() {
            return isBlock;
        }

        public void setIsBlock(int isBlock) {
            this.isBlock = isBlock;
        }

        public String getMacVendor() {
            return macVendor;
        }

        public void setMacVendor(String macVendor) {
            this.macVendor = macVendor;
        }

        public String getMacIcon() {
            return macIcon;
        }

        public void setMacIcon(String macIcon) {
            this.macIcon = macIcon;
        }

        @Override
        public String toString() {
            return "MobileBean{" +
                    "mac='" + mac + '\'' +
                    ", name='" + name + '\'' +
                    ", onTime=" + onTime +
                    ", offTime=" + offTime +
                    ", status=" + status +
                    ", note=" + note +
                    ", nodeId='" + nodeId + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", isDelete=" + isDelete +
                    ", isBlock=" + isBlock +
                    ", macVendor='" + macVendor + '\'' +
                    ", macIcon='" + macIcon + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mac);
            dest.writeString(this.name);
            dest.writeInt(this.onTime);
            dest.writeInt(this.offTime);
            dest.writeInt(this.status);
            dest.writeString(this.note);
            dest.writeString(this.nodeId);
            dest.writeLong(this.createTime);
            dest.writeLong(this.updateTime);
            dest.writeInt(this.isDelete);
            dest.writeInt(this.isBlock);
            dest.writeString(this.macVendor);
            dest.writeString(this.macIcon);
            dest.writeString(this.ip);
        }

        public MobileBean() {
        }

        protected MobileBean(Parcel in) {
            this.mac = in.readString();
            this.name = in.readString();
            this.onTime = in.readInt();
            this.offTime = in.readInt();
            this.status = in.readInt();
            this.note = in.readString();
            this.nodeId = in.readString();
            this.createTime = in.readLong();
            this.updateTime = in.readLong();
            this.isDelete = in.readInt();
            this.isBlock = in.readInt();
            this.macVendor = in.readString();
            this.macIcon = in.readString();
            this.ip = in.readString();
        }

        public static final Creator<MobileBean> CREATOR = new Creator<MobileBean>() {
            @Override
            public MobileBean createFromParcel(Parcel source) {
                return new MobileBean(source);
            }

            @Override
            public MobileBean[] newArray(int size) {
                return new MobileBean[size];
            }
        };
    }

    @Override
    public String toString() {
        return "MobileListBean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
