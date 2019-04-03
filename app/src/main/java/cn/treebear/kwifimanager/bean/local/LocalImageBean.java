package cn.treebear.kwifimanager.bean.local;

public class LocalImageBean {
    private String thumbPath;

    private long dateAdded;

    private String date;

    private String filepath;

    private boolean select;

    public LocalImageBean() {
    }

    public LocalImageBean(String thumbPath, long dateAdded, String filepath) {
        this.thumbPath = thumbPath;
        this.dateAdded = dateAdded;
        this.filepath = filepath;
    }
    public LocalImageBean(String thumbPath, long dateAdded, String date,String filepath) {
        this.thumbPath = thumbPath;
        this.dateAdded = dateAdded;
        this.date = date;
        this.filepath = filepath;
    }
    public LocalImageBean(String thumbPath, String dateAdded, String filepath) {
        this.thumbPath = thumbPath;
        this.date = dateAdded;
        this.filepath = filepath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "LocalImageBean{" +
                "thumbPath='" + thumbPath + '\'' +
                ", dateAdded=" + dateAdded +
                ", date='" + date + '\'' +
                ", filepath='" + filepath + '\'' +
                ", select=" + select +
                '}';
    }
}
