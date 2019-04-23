package cn.treebear.kwifimanager.bean.local;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.treebear.kwifimanager.config.ConstConfig;

public class LocalFamilyGalleryBean implements MultiItemEntity {

    private String title;

    private String content;

    private String shareUrl;

    @ConstConfig.FamilyGalleryType
    private int type;

    private List<LocalImageBean> images;

    public LocalFamilyGalleryBean(String title, String content, List<LocalImageBean> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<LocalImageBean> getImages() {
        return images;
    }

    public void setImages(List<LocalImageBean> images) {
        this.images = images;
    }

    @Override
    public int getItemType() {
        if (images.size() > 5) {
            return type = ConstConfig.FAMILY_GALLERY_TYPE_5;
        } else if (images.size() > 3) {
            return type = ConstConfig.FAMILY_GALLERY_TYPE_3;
        } else {
            return type = ConstConfig.FAMILY_GALLERY_TYPE_1;
        }
    }
}
