package cn.treebear.kwifimanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeControlbean {

    /**
     * total : 2
     * page : [{"id":111111111111,"nodeId":"","name":"","st":"","et":"","wt":5,"sMac":"","op":1}]
     */

    private int total;
    private List<TimeControl> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TimeControl> getPage() {
        return page;
    }

    public void setPage(List<TimeControl> page) {
        this.page = page;
    }

    public static class TimeControl implements Parcelable, Serializable {
        /**
         * id : 111111111111
         * nodeId :
         * name :
         * st :
         * et :
         * wt : 5
         * sMac :
         * op : 1
         */

        private long id;
        private String nodeId;
        private String name;
        private String st;
        private String et;
        private int wt;
        private String mac;
        private ArrayList<String> sMac;
        private int op;

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public String getSt() {
            return st;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public String getEt() {
            return et;
        }

        public void setEt(String et) {
            this.et = et;
        }

        public int getWt() {
            return wt;
        }

        public void setWt(int wt) {
            this.wt = wt;
        }

        public ArrayList<String> getsMac() {
            return sMac;
        }

        public void setsMac(ArrayList<String> sMac) {
            this.sMac = sMac;
        }

        public int getOp() {
            return op;
        }

        public void setOp(int op) {
            this.op = op;
        }

        @Override
        public String toString() {
            return "TimeControl{" +
                    "id=" + id +
                    ", nodeId='" + nodeId + '\'' +
                    ", name='" + name + '\'' +
                    ", st='" + st + '\'' +
                    ", et='" + et + '\'' +
                    ", wt=" + wt +
                    ", sMac='" + sMac + '\'' +
                    ", op=" + op +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.nodeId);
            dest.writeString(this.name);
            dest.writeString(this.st);
            dest.writeString(this.et);
            dest.writeInt(this.wt);
            dest.writeStringList(this.sMac);
            dest.writeInt(this.op);
        }

        public TimeControl() {
        }

        public TimeControl(long id, int op) {
            this.id = id;
            this.op = op;
        }

        protected TimeControl(Parcel in) {
            this.id = in.readLong();
            this.nodeId = in.readString();
            this.name = in.readString();
            this.st = in.readString();
            this.et = in.readString();
            this.wt = in.readInt();
            this.sMac = in.createStringArrayList();
            this.op = in.readInt();
        }

        public static final Creator<TimeControl> CREATOR = new Creator<TimeControl>() {
            @Override
            public TimeControl createFromParcel(Parcel source) {
                return new TimeControl(source);
            }

            @Override
            public TimeControl[] newArray(int size) {
                return new TimeControl[size];
            }
        };
    }

    @Override
    public String toString() {
        return "TimeControlbean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
