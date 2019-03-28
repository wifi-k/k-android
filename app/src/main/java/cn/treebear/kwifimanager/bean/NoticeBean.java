package cn.treebear.kwifimanager.bean;

/**
 * 通知消息
 *
 * @author Administrator
 */
public class NoticeBean {
    private long id;

    private String content;

    private String url;
    /**
     * 消息活动类型
     */
    private String title;
    /**
     * 消息活动时间
     */
    private long mills;

    public NoticeBean(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public NoticeBean(String content, String title, long mills) {
        this.content = content;
        this.title = title;
        this.mills = mills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMills() {
        return mills;
    }

    public void setMills(long mills) {
        this.mills = mills;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
