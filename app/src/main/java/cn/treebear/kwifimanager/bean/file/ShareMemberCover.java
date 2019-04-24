package cn.treebear.kwifimanager.bean.file;

import java.util.List;

public class ShareMemberCover {

    /**
     * total : 111
     * page : [{"id":111111111111,"name":"","avatar":""},{}]
     */

    private int total;
    private List<MemberBean> page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MemberBean> getPage() {
        return page;
    }

    public void setPage(List<MemberBean> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ShareMemberCover{" +
                "total=" + total +
                ", page=" + page +
                '}';
    }
}
