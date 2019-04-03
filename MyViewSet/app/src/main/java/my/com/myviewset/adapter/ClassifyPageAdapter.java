package my.com.myviewset.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import my.com.myviewset.bean.ClassifyBean;
import my.com.myviewset.fragment.ClassifyPageFragment;
import my.com.myviewset.widget.PagerSlidingTabStrip;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ClassifyPageAdapter extends MainSectionsPagerAdapter {

    private List<ClassifyBean> mDataList = new ArrayList<>();
    private List<ClassifyPageFragment> mFragmentList = new ArrayList<>();
    private PagerSlidingTabStrip mTabStrip;

    public ClassifyPageAdapter(FragmentManager fm, PagerSlidingTabStrip tabStrip) {
        super(fm);
        mTabStrip = tabStrip;
    }

    public void setDataList(List<ClassifyBean> dataList, List<ClassifyPageFragment> fragmentList) {
        mDataList = dataList;
        mFragmentList = fragmentList;
        mTabStrip.notifyDataSetChanged();
        notifyDataSetChanged();
    }

    public List<ClassifyPageFragment> getDataList() {
        return mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        ClassifyPageFragment pagerFragment = mFragmentList.get(position);
        return pagerFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position).getCategoryName();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
