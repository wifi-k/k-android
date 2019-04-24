package cn.treebear.kwifimanager.bean.file;

import java.util.List;

public class ShareDirFileListCover {

    /**
     * total : 11
     * page : [{"shareId":"上传批次ID","createTime":111111111111,"user":{"id":111111111111111,"name":"","avatar":""},"files":[{"thumbnail":""},{}]},{}]
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
         * shareId : 上传批次ID
         * createTime : 111111111111
         * user : {"id":111111111111111,"name":"","avatar":""}
         * files : [{"thumbnail":""},{}]
         */

        private String shareId;
        private long createTime;
        private MemberBean user;
        private List<FilesBean> files;

        public String getShareId() {
            return shareId;
        }

        public void setShareId(String shareId) {
            this.shareId = shareId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public MemberBean getUser() {
            return user;
        }

        public void setUser(MemberBean user) {
            this.user = user;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        @Override
        public String toString() {
            return "PageBean{" +
                    "shareId='" + shareId + '\'' +
                    ", createTime=" + createTime +
                    ", user=" + user +
                    ", files=" + files +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShareDirFileListCover{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
