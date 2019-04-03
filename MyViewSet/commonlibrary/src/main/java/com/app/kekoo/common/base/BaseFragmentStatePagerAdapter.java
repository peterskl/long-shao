package com.app.kekoo.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.kekoo.common.commonutils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * FragmentPagerAdapter适用于Fragment比较少的情况，因为我们会把每一个Fragment保存在内存中，
 * 不用每次切换的时候，去保存现场，切换回来在重新创建，所以用户体验比较好。
 * 而对于我们的Fragment比较多的情况，
 * 我们需要切换的时候销毁以前的Fragment以释放内存，
 * 就可以使用FragmentStatePagerAdapter
 * Created by ${mms} on 2017/3/10.
 */

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitles;


    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !CollectionUtils.isNullOrEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    // 动态设置我们标题的方法
    public void setPageTitle(int position, String title)
    {
        if(position >= 0 && position < mTitles.size())
        {
            mTitles.set(position, title);
            notifyDataSetChanged();
        }
    }
}
