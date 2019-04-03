package my.com.myviewset.adapter;

/**
 * Created by n5378 on 2017/6/2.
 */

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.app.kekoo.common.base.BaseFragment;

import java.util.ArrayList;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainSectionsPagerAdapter extends FragmentStatePagerAdapter {

    //对外提供属性
    public BaseFragment mCurrentFragment;

    private ArrayList<BaseFragment> mFragments;

    public MainSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = object instanceof BaseFragment ? (BaseFragment) object : null;
    }

    /**
     * fix bug
     * https://code.google.com/p/android/issues/detail?id=218912
     *
     * @param container
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (Exception ignore) {

        }
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    public ArrayList<BaseFragment> getFragments() {
        return mFragments;
    }

    public void setFragments(ArrayList<BaseFragment> fragments) {
        mFragments = fragments;
    }

    public void destroy() {
        if (mFragments != null) {
            mFragments.clear();
            mFragments = null;
        }
    }

}