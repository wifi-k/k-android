package cn.treebear.kwifimanager.bean.file;

public class NodeFileResponse {
    /**
     * wait : 300000000000
     * url :
     */

    private long wait;
    private String url;

    public long getWait() {
        return wait;
    }

    public void setWait(long wait) {
        this.wait = wait;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NodeFileResponse{" +
                "wait=" + wait +
                ", url='" + url + '\'' +
                '}';
    }
}
