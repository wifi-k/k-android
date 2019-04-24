package cn.treebear.kwifimanager.bean.file;

import com.chad.library.adapter.base.entity.SectionEntity;

public class FamilyGallerySection extends SectionEntity<FamilyGalleryDetailBean> {

    public FamilyGallerySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public FamilyGallerySection(FamilyGalleryDetailBean localImageBean) {
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
