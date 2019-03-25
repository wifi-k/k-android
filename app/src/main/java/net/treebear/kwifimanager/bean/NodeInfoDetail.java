package net.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class NodeInfoDetail implements Serializable{
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

    public static class NodeBean implements Serializable, Parcelable {
        /**
         * nodeId : afad
         * name :
         * firmware : 当前固件版本
         * manufactory : 设备制造商
         * model : 型号
         * status : 在线状态
         * bindTime : 绑定时间
         * isShare : 1
         * firmwareUpgrade : 固件升级版本号
         * isSelect : 1 当前选择节点
         */

        private String nodeId;
        private String name;
        private String firmware;
        private String manufactory;
        private String model;
        private int status;
        private String bindTime;
        private int isShare;
        private String firmwareUpgrade;
        private int isSelect;

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirmware() {
            return firmware;
        }

        public void setFirmware(String firmware) {
            this.firmware = firmware;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBindTime() {
            return bindTime;
        }

        public void setBindTime(String bindTime) {
            this.bindTime = bindTime;
        }

        public int getIsShare() {
            return isShare;
        }

        public void setIsShare(int isShare) {
            this.isShare = isShare;
        }

        public String getFirmwareUpgrade() {
            return firmwareUpgrade;
        }

        public void setFirmwareUpgrade(String firmwareUpgrade) {
            this.firmwareUpgrade = firmwareUpgrade;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nodeId);
            dest.writeString(this.name);
            dest.writeString(this.firmware);
            dest.writeString(this.manufactory);
            dest.writeString(this.model);
            dest.writeInt(this.status);
            dest.writeString(this.bindTime);
            dest.writeInt(this.isShare);
            dest.writeString(this.firmwareUpgrade);
        }

        public NodeBean() {
        }

        protected NodeBean(Parcel in) {
            this.nodeId = in.readString();
            this.name = in.readString();
            this.firmware = in.readString();
            this.manufactory = in.readString();
            this.model = in.readString();
            this.status = in.readInt();
            this.bindTime = in.readString();
            this.isShare = in.readInt();
            this.firmwareUpgrade = in.readString();
        }

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

        @Override
        public String toString() {
            return "NodeBean{" +
                    "nodeId='" + nodeId + '\'' +
                    ", name='" + name + '\'' +
                    ", firmware='" + firmware + '\'' +
                    ", manufactory='" + manufactory + '\'' +
                    ", model='" + model + '\'' +
                    ", status=" + status +
                    ", bindTime='" + bindTime + '\'' +
                    ", isShare=" + isShare +
                    ", firmwareUpgrade='" + firmwareUpgrade + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NodeInfoDetail{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
