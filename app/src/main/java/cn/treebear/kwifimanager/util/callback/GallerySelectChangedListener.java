package cn.treebear.kwifimanager.util.callback;

import java.util.ArrayList;

import cn.treebear.kwifimanager.bean.local.LocalImageBean;

public interface GallerySelectChangedListener {

    void onSelectChange(ArrayList<LocalImageBean> beans, LocalImageBean changed);

}
