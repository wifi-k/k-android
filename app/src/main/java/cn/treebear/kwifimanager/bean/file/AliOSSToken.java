package cn.treebear.kwifimanager.bean.file;

public class AliOSSToken {

    /**
     * domain : oss-cn-hangzhou.aliyuncs.com
     * bucket : tbc-nodefile
     * accessKeyId :
     * accessKeySecret :
     * securityToken :
     */

    private String domain;
    private String bucket;
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    @Override
    public String toString() {
        return "AliOSSToken{" +
                "domain='" + domain + '\'' +
                ", bucket='" + bucket + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", securityToken='" + securityToken + '\'' +
                '}';
    }
}
