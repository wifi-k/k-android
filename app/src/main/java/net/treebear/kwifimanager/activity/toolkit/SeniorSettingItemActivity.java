package net.treebear.kwifimanager.activity.toolkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.adapter.TextSettingAdapter;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.bean.ItemBean;
import net.treebear.kwifimanager.config.Keys;
import net.treebear.kwifimanager.config.Values;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class SeniorSettingItemActivity extends BaseActivity {

    @BindView(R.id.rv_setting_item)
    RecyclerView rvSettingItem;
    private String titleText;
    private ArrayList<ItemBean> itemData = new ArrayList<>();
    private int settingItemType;
    private TextSettingAdapter settingAdapter;
    private int currentPosition;

    @Override
    public int layoutId() {
        return R.layout.activity_senior_setting_item;
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
    protected void initView() {
        setTitleBack(titleText, getString(R.string.save));
        settingAdapter = new TextSettingAdapter(itemData);
        rvSettingItem.setLayoutManager(new LinearLayoutManager(this));
        rvSettingItem.setAdapter(settingAdapter);
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
        intent.putExtra(Keys.NAME,itemData.get(currentPosition).getName());
        intent.putExtra(Keys.POSITION,currentPosition);
        intent.putExtra(Keys.TYPE,settingItemType);
        setResult(Values.SENIOR_SETTING,intent);
        onTitleLeftClick();
    }
}
