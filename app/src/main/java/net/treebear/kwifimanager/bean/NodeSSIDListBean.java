package net.treebear.kwifimanager.bean;

import java.util.List;

public class NodeSSIDListBean {
    /**
     * total : 2
     * page : [{"nodeId":"","freq":1,"ssid":"","passwd":"","rssi":-70},{"nodeId":"","freq":2,"ssid":"","passwd":"","rssi":-60}]
     */

    private int total;
    private List<PageBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PageBean> getPage() {
        return page;
    }

    public void setPage(List<PageBean> page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * nodeId :
         * freq : 1
         * ssid :
         * passwd :
         * rssi : -70
         */

        private String nodeId;
        private int freq;
        private String ssid;
        private String passwd;
        private int rssi;

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public int getRssi() {
            return rssi;
        }

        public void setRssi(int rssi) {
            this.rssi = rssi;
        }

        @Override
        public String toString() {
            return "NodeBean{" +
                    "nodeId='" + nodeId + '\'' +
                    ", freq=" + freq +
                    ", ssid='" + ssid + '\'' +
                    ", passwd='" + passwd + '\'' +
                    ", rssi=" + rssi +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NodeSSIDListBean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
