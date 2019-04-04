package cn.treebear.kwifimanager.bean;

import java.util.List;

public class ChildrenListBean {
    /**
     * total : 2
     * page : [{"nodeId":"","mac":"1","year":2019,"week":15,"totalTime":11111111111,"remark":"描述文案","macNote":"mac备注","hostName":"设备名称","macVendor":"机型","macIcon":"手机图片"},{}]
     */

    private int total;
    private List<ChildrenBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChildrenBean> getPage() {
        return page;
    }

    public void setPage(List<ChildrenBean> page) {
        this.page = page;
    }

    public static class ChildrenBean {
        /**
         * nodeId :
         * mac : 1
         * year : 2019
         * week : 15
         * totalTime : 11111111111
         * remark : 描述文案
         * macNote : mac备注
         * hostName : 设备名称
         * macVendor : 机型
         * macIcon : 手机图片
         */

        private String nodeId;
        private String mac;
        private int year;
        private int week;
        private long totalTime;
        private String remark;
        private String macNote;
        private String hostName;
        private String macVendor;
        private String macIcon;

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

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(long totalTime) {
            this.totalTime = totalTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMacNote() {
            return macNote;
        }

        public void setMacNote(String macNote) {
            this.macNote = macNote;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
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
    }
}
