package cn.treebear.kwifimanager.bean;

import java.util.List;

public class FamilyMemberCover {

    /**
     * total : 1
     * page : [{"userId":11,"userName":"","userMobile":"","userAvatar":"","nodeId":"","nodeName":"","role":0}]
     */

    private int total;
    private List<FamilyMemberBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FamilyMemberBean> getPage() {
        return page;
    }

    public void setPage(List<FamilyMemberBean> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "FamilyMemberCover{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
