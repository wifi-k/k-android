package cn.treebear.kwifimanager.bean.file;

public class FilesBean {
    /**
     * thumbnail :
     */

    private String thumbnail;

    private boolean isTail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isTail() {
        return isTail;
    }

    public void setTail(boolean tail) {
        isTail = tail;
    }

    @Override
    public String toString() {
        return "FilesBean{" +
                "thumbnail='" + thumbnail + '\'' +
                ", isTail=" + isTail +
                '}';
    }
}
