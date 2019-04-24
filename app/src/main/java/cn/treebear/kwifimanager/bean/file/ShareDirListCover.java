package cn.treebear.kwifimanager.bean.file;

import java.util.List;

public class ShareDirListCover {

    /**
     * total : 12
     * page : [{"name":"目录名称","user":{"id":111111111111,"name":"","avatar":""},"files":[{"thumbnail":""},{}]},{}]
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
         * name : 目录名称
         * user : {"id":111111111111,"name":"","avatar":""}
         * files : [{"thumbnail":""},{}]
         */

        private String name;
        private MemberBean user;
        private List<FilesBean> files;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
                    "name='" + name + '\'' +
                    ", user=" + user +
                    ", files=" + files +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShareDirListCover{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
