package cn.treebear.kwifimanager.bean.local;

import java.util.List;

public class LocalFamilyGalleryDetailBean {

    /**
     * shareId : id
     * createTime : 11111111111111
     * user : {"id":1111111111111,"name":"","avatar":""}
     * files : [{"thumbnail":""},{}]
     */

    private String shareId;
    private long createTime;
    private UserBean user;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class UserBean {
        /**
         * id : 1111111111111
         * name :
         * avatar :
         */

        private long id;
        private String name;
        private String avatar;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class FilesBean {
        /**
         * thumbnail :
         */

        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
