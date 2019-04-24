package cn.treebear.kwifimanager.bean.file;

import java.util.List;

public class FileListCover {
    /**
     * total : 总数
     * page : [{"sourceId":"唯一ID","nodeId":"","userId":11111111111,"path":"节点路径","name":"文件名","type":"文件类型","sourceTime":111111111111111,"createTime":11111111111,"updateTime":1111111111,"thumbnail":"缩略图地址"},{}]
     */

    private String total;
    private List<PageBean> page;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
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
         * sourceId : 唯一ID
         * nodeId :
         * userId : 11111111111
         * path : 节点路径
         * name : 文件名
         * type : 文件类型
         * sourceTime : 111111111111111
         * createTime : 11111111111
         * updateTime : 1111111111
         * thumbnail : 缩略图地址
         */

        private String sourceId;
        private String nodeId;
        private long userId;
        private String path;
        private String name;
        private String type;
        private long sourceTime;
        private long createTime;
        private long updateTime;
        private String thumbnail;

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSourceTime() {
            return sourceTime;
        }

        public void setSourceTime(long sourceTime) {
            this.sourceTime = sourceTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public String toString() {
            return "PageBean{" +
                    "sourceId='" + sourceId + '\'' +
                    ", nodeId='" + nodeId + '\'' +
                    ", userId=" + userId +
                    ", path='" + path + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", sourceTime=" + sourceTime +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FileListCover{" +
                "total='" + total + '\'' +
                ", page=" + page +
                '}';
    }
}
