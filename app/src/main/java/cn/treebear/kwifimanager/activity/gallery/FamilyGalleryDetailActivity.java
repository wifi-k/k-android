package cn.treebear.kwifimanager.activity.gallery;

import android.os.Bundle;

import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.config.Keys;

public class FamilyGalleryDetailActivity extends BaseActivity {

    private long galleryId;

    @Override
    public int layoutId() {
        return R.layout.activity_family_gallery_detail;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            galleryId = params.getLong(Keys.ID, -1);
        }
    }

    @Override
    protected void initView() {

    }
}
