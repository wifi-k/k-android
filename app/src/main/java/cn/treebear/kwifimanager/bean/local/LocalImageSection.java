package cn.treebear.kwifimanager.bean.local;

import com.chad.library.adapter.base.entity.SectionEntity;

public class LocalImageSection extends SectionEntity<LocalImageBean> {

    public LocalImageSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public LocalImageSection(LocalImageBean localImageBean) {
        super(localImageBean);
    }

    @Override
    public String toString() {
        return "LocalImageSection{" +
                "isHeader=" + isHeader +
                ", t=" + t +
                ", header='" + header + '\'' +
                '}';
    }
}
