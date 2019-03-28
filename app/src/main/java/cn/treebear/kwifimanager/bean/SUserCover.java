package cn.treebear.kwifimanager.bean;

public class SUserCover {

    private int nodeSize;

    private ServerUserInfo user;

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }

    public ServerUserInfo getUser() {
        return user;
    }

    public void setUser(ServerUserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SUserCover{" +
                "nodeSize=" + nodeSize +
                ", user=" + user +
                '}';
    }
}
