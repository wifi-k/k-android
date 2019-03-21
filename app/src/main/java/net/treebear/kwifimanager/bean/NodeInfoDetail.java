package net.treebear.kwifimanager.bean;

import java.util.List;

public class NodeInfoDetail {
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

    public static class NodeBean {
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
         */

        private String nodeId;
        private String name;
        private String firmware;
        private String manufactory;
        private String model;
        private String status;
        private String bindTime;
        private int isShare;
        private String firmwareUpgrade;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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
    }
}
