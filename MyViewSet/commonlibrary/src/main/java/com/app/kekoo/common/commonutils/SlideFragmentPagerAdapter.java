package com.app.kekoo.common.commonutils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: mms
 * Data: 16/7/30 下午11:27
 * Des:
 * version:
 */
public class SlideFragmentPagerAdapter extends FragmentPagerAdapter {
    public SlideFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        if (position % 2 == 0)
//            return new FragmentItem2();
//        return new FragmentItem1();
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    private View mCurrentView;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            mCurrentView = (View) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            mCurrentView = fragment.getView();
        }
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab"+position;
    }
}