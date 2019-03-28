package cn.treebear.kwifimanager.bean;

import java.io.Serializable;
import java.util.List;

public class HealthyModelBean implements Serializable {
    /**
     * nodeId : aa
     * op : 1
     * wifiBeans : [{"freq":0,"rssi":5,"timer":[{"startTime":"23:00","endTime":"06:00"},{}]},{}]
     */

    private String nodeId;
    private int op;
    private String wifi;
    private List<WifiBean> wifiBeans;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getOp() {
        return op;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public List<WifiBean> getWifiBeans() {
        return wifiBeans;
    }

    public void setWifiBeans(List<WifiBean> wifiBeans) {
        this.wifiBeans = wifiBeans;
    }

    public static class WifiBean implements Serializable {
        /**
         * freq : 0
         * rssi : 5
         * timer : [{"startTime":"23:00","endTime":"06:00"},{}]
         */

        private int freq;
        private int rssi;
        private List<TimerBean> timer;

        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public int getRssi() {
            return rssi;
        }

        public void setRssi(int rssi) {
            this.rssi = rssi;
        }

        public List<TimerBean> getTimer() {
            return timer;
        }

        public void setTimer(List<TimerBean> timer) {
            this.timer = timer;
        }

        public static class TimerBean implements Serializable {
            /**
             * startTime : 23:00
             * endTime : 06:00
             */

            private String startTime;
            private String endTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            @Override
            public String toString() {
                return "TimerBean{" +
                        "startTime='" + startTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "WifiBean{" +
                    "freq=" + freq +
                    ", rssi=" + rssi +
                    ", timer=" + timer +
                    '}';
        }
    }
}
