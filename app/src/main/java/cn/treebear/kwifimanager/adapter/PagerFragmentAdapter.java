package cn.treebear.kwifimanager.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class PagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private FragmentManager mFragmentManager;
    private int positionType = POSITION_UNCHANGED;
    ;

    public PagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
        mFragmentManager = fm;
    }

    /**
     * 在此方法中找到需要更新的位置返回POSITION_NONE，否则返回POSITION_UNCHANGED即可
     */
    @Override
    public int getItemPosition(Object object) {
        int nowerPosition = positionType;
        positionType = POSITION_UNCHANGED;
        return nowerPosition;
    }


    /**
     * 将指定位置的Fragment替换 / 更新为新的Fragment，同 {
     *
     * @param position    旧Fragment的位置
     * @param newFragment 新Fragment
     * @link #replaceFragment(Fragment oldFragment, Fragment newFragment)
     * }
     */
    public void replaceFragment(int position, Fragment newFragment) {
        Fragment oldFragment = mFragmentList.get(position);
        mFragmentList.set(position, newFragment);
        removeFragmentInternal(oldFragment);
        positionType = POSITION_NONE;
        notifyDataSetChanged();
    }

    /**
     * 从Transaction移除Fragment
     *
     * @param fragment 目标Fragment
     */
    private void removeFragmentInternal(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitNow();
    }


    /**
     * 此方法不用position做返回值即可破解fragment tag异常的错误
     */
    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode，其实这里不用hashCode用自定义的可以关联当前Item对象的唯一值也可以，只要不是直接返回position
        return position;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
