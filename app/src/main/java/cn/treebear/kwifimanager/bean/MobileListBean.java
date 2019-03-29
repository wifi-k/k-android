package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class MobileListBean implements Serializable {


    /**
     * total : 2
     * page : [{"nodeId":"","mac":"","name":"主机名","onTime":"最近在线时间戳","offTime":"最近离线时间戳","note":"设备备注名","status":0,"block":0},{}]
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
         * nodeId :
         * mac :
         * name : 主机名
         * onTime : 最近在线时间戳
         * offTime : 最近离线时间戳
         * note : 设备备注名
         * status : 0
         * block : 0
         */

        private String nodeId;
        private String mac;
        private String name;
        private long onTime;
        private long offTime;
        private String note;
        private int status;
        private int block;

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
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

        public long getOnTime() {
            return onTime;
        }

        public void setOnTime(long onTime) {
            this.onTime = onTime;
        }

        public long getOffTime() {
            return offTime;
        }

        public void setOffTime(long offTime) {
            this.offTime = offTime;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBlock() {
            return block;
        }

        public void setBlock(int block) {
            this.block = block;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nodeId);
            dest.writeString(this.mac);
            dest.writeString(this.name);
            dest.writeLong(this.onTime);
            dest.writeLong(this.offTime);
            dest.writeString(this.note);
            dest.writeInt(this.status);
            dest.writeInt(this.block);
        }

        public MobileBean() {
        }

        protected MobileBean(Parcel in) {
            this.nodeId = in.readString();
            this.mac = in.readString();
            this.name = in.readString();
            this.onTime = in.readLong();
            this.offTime = in.readLong();
            this.note = in.readString();
            this.status = in.readInt();
            this.block = in.readInt();
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

        @Override
        public String toString() {
            return "MobileBean{" +
                    "nodeId='" + nodeId + '\'' +
                    ", mac='" + mac + '\'' +
                    ", name='" + name + '\'' +
                    ", onTime='" + onTime + '\'' +
                    ", offTime='" + offTime + '\'' +
                    ", note='" + note + '\'' +
                    ", status=" + status +
                    ", block=" + block +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MobileListBean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
