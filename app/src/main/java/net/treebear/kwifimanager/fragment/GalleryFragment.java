package net.treebear.kwifimanager.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseFragment;
import net.treebear.kwifimanager.util.TLog;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class GalleryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_ai_gallery)
    TextView tvAiGallery;
    @BindView(R.id.tv_all_gallery)
    TextView tvAllGallery;
    @BindView(R.id.tv_share_gallery)
    TextView tvShareGallery;
    @BindView(R.id.rl_tab_wrapper)
    RelativeLayout rlTabWrapper;

    @Override
    public int layoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        rlTabWrapper.setEnabled(false);
        listenScroll();
    }

    private void listenScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                TLog.i("dx = %s, dy = %s", dx, dy);
                // 获取header坐标y , 一直header height
                // 坐标 (height - y) / height = 比例
                // 比例 * 1 = alpha
                // 比例 * （屏幕width / 1.2） = title右边距
            }
        });
    }

}
