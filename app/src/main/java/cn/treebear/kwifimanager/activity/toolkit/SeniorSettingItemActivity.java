package cn.treebear.kwifimanager.activity.toolkit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.adapter.TextSettingAdapter;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.ItemBean;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;

/**
 * @author Administrator
 */
public class SeniorSettingItemActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView rvSettingItem;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.tv_empty_view)
    TextView tvEmptyView;
    private String titleText;
    private ArrayList<ItemBean> itemData = new ArrayList<>();
    private int settingItemType;
    private TextSettingAdapter settingAdapter;
    private int currentPosition;

    @Override
    public int layoutId() {
        return R.layout.layout_title_recyclerview;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            titleText = params.getString(Keys.TITLE, "");
            settingItemType = params.getInt(Keys.TYPE, 0);
            currentPosition = params.getInt(Keys.POSITION, -1);
            ArrayList<String> arr = params.getStringArrayList(Keys.SETTINGS_ITEM_DATA);
            if (arr != null) {
                for (int i = 0; i < arr.size(); i++) {
                    itemData.add(new ItemBean(arr.get(i), currentPosition == i));
                }
            }
        }
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        swipeRefreshLayout.setRefreshing(false);
        super.onLoadFail(resultData, resultMsg, resultCode);
    }

    @Override
    protected void initView() {
        setTitleBack(titleText, getString(R.string.save));
        settingAdapter = new TextSettingAdapter(itemData);
        rvSettingItem.setLayoutManager(new LinearLayoutManager(this));
        rvSettingItem.setAdapter(settingAdapter);
        settingAdapter.setEnableLoadMore(false);
        settingAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (currentPosition >= 0 && currentPosition < itemData.size()) {
                itemData.get(currentPosition).setChecked(false);
            }
            itemData.get(position).setChecked(true);
            currentPosition = position;
            settingAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onTitleRightClick() {
        Intent intent = new Intent();
        intent.putExtra(Keys.NAME, itemData.get(currentPosition).getName());
        intent.putExtra(Keys.POSITION, currentPosition);
        intent.putExtra(Keys.TYPE, settingItemType);
        setResult(Values.REQUEST_SENIOR_SETTING, intent);
        onTitleLeftClick();
    }
}
