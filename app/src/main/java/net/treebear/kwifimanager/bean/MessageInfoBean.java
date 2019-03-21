package net.treebear.kwifimanager.bean;

import java.util.List;

public class MessageInfoBean {
    /**
     * total : 2
     * page : [{"title":"","type":1,"content":"","createTime":"消息时间戳"}]
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
         * title :
         * type : 1
         * content :
         * createTime : 消息时间戳
         */

        private String title;
        private int type;
        private String content;
        private long createTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "NodeBean{" +
                    "title='" + title + '\'' +
                    ", type=" + type +
                    ", content='" + content + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MessageInfoBean{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
