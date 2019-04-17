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

    @Override
    public String toString() {
        return "MobileListBean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }

    public static class MobileBean implements Serializable, Parcelable {

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
        /**
         * id : 5
         * mac : 68:f7:28:77:f4:51
         * name : PC-20190221OGLK
         * onTime : 1554873435
         * offTime : 0
         * status : 1
         * note : PC-000001
         * nodeId : 214216BF0F3D
         * createTime : 1553777328892
         * updateTime : 1554886567312
         * isDelete : 0
         * isBlock : 1
         * macVendor : default
         * macIcon : http://test.developer.qiniu.famwifi.com/ic_default.png?e=1554887333&token=yl1JVmF9Vx6HuZ1dapbIEbZ4lb__T0Z71tXZ71zj:OSl_Va5sDavTQfstXHRmOQwUlKM=
         * localIp :
         * isRecord : 1
         * isOnline : 0
         */

        private int id;
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
        private String localIp;
        private int isRecord;
        private int isOnline;

        public MobileBean() {
        }

        protected MobileBean(Parcel in) {
            this.id = in.readInt();
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
            this.localIp = in.readString();
            this.isRecord = in.readInt();
            this.isOnline = in.readInt();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getLocalIp() {
            return localIp;
        }

        public void setLocalIp(String localIp) {
            this.localIp = localIp;
        }

        public int getIsRecord() {
            return isRecord;
        }

        public void setIsRecord(int isRecord) {
            this.isRecord = isRecord;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        @Override
        public String toString() {
            return "MobileBean{" +
                    "id=" + id +
                    ", mac='" + mac + '\'' +
                    ", name='" + name + '\'' +
                    ", onTime=" + onTime +
                    ", offTime=" + offTime +
                    ", status=" + status +
                    ", note='" + note + '\'' +
                    ", nodeId='" + nodeId + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", isDelete=" + isDelete +
                    ", isBlock=" + isBlock +
                    ", macVendor='" + macVendor + '\'' +
                    ", macIcon='" + macIcon + '\'' +
                    ", localIp='" + localIp + '\'' +
                    ", isRecord=" + isRecord +
                    ", isOnline=" + isOnline +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
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
            dest.writeString(this.localIp);
            dest.writeInt(this.isRecord);
            dest.writeInt(this.isOnline);
        }
    }
}
