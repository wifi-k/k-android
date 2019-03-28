package cn.treebear.kwifimanager.bean;

public class QiNiuUserBean {
    /**
     * token :
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "QiNiuUserBean{" +
                "token='" + token + '\'' +
                '}';
    }
}
