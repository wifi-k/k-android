package cn.treebear.kwifimanager.bean.file;

public class FilesBean {
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

    @Override
    public String toString() {
        return "FilesBean{" +
                "thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
