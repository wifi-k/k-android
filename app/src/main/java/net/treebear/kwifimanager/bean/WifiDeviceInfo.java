package net.treebear.kwifimanager.bean;

/**
 * @author Administrator
 * @date 2017/11/22
 */

public class WifiDeviceInfo {


    /**
     * {
     * "id": "节点序列号",
     * "wan": {  // 1-pppoe 2-static 3-dynamic
     * "ip":"", "netmask":"", "gateway":"" , "dns1":"", "dns2":"", "type":1, "name":"pppoe账号", "passwd":"账号密码"
     * }
     * }
     */
    private boolean connect;
    private String id;
    private String token;
    /**
     * wan : {"ip":"","netmask":"","gateway":"","dns1":"","dns2":"","type":1,"name":"pppoe账号","passwd":"账号密码"}
     */

    private WanBean wan;

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WanBean getWan() {
        if (wan == null){
            wan = new WanBean();
        }
        return wan;
    }

    public void setWan(WanBean wan) {
        this.wan = wan;
    }

    public static class WanBean {
        /**
         * ip :
         * netmask :
         * gateway :
         * dns1 :
         * dns2 :
         * type : 1
         * name : pppoe账号
         * passwd : 账号密码
         */

        private String ip;
        private String netmask;
        private String gateway;
        private String dns1;
        private String dns2;
        private int type;
        private String name;
        private String passwd;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getNetmask() {
            return netmask;
        }

        public void setNetmask(String netmask) {
            this.netmask = netmask;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getDns1() {
            return dns1;
        }

        public void setDns1(String dns1) {
            this.dns1 = dns1;
        }

        public String getDns2() {
            return dns2;
        }

        public void setDns2(String dns2) {
            this.dns2 = dns2;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        @Override
        public String toString() {
            return "WanBean{" +
                    "ip='" + ip + '\'' +
                    ", netmask='" + netmask + '\'' +
                    ", gateway='" + gateway + '\'' +
                    ", dns1='" + dns1 + '\'' +
                    ", dns2='" + dns2 + '\'' +
                    ", type=" + type +
                    ", name='" + name + '\'' +
                    ", passwd='" + passwd + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WifiDeviceInfo{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", wan=" + wan +
                '}';
    }
}
