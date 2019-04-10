package cn.treebear.kwifimanager.widget.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import cn.treebear.kwifimanager.widget.Dismissable;

public class ShareGalleryPop implements Dismissable {

    private Context mContext;
    private PopupWindow popupWindow;

    public ShareGalleryPop(Context mContext) {
        this.mContext = mContext;
    }

    public void show(View parent){
        if(popupWindow == null){
            initPopupWindow();
        }
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private void initPopupWindow() {

    }

    @Override
    public void dismiss() {

    }
}
