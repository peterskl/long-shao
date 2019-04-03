package com.app.kekoo.common.commonutils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by ${mms} on 2016/11/22.
 */

public class FragmentUtils {

    public Context act;
    public FragmentManager fm;
    public Fragment fragment;


    /**
     * @param act
     * activity嵌套fragment使用
     *
     */
    public FragmentUtils(AppCompatActivity act) {
        this.act = act;
        fm = act.getSupportFragmentManager();

    }

    /**
     * @param fragment
     * ragment嵌套fragment使用
     *
     */
    public FragmentUtils(Fragment fragment) {
        this.fragment = fragment;
        this.act = fragment.getActivity();
        fm = fragment.getChildFragmentManager();

    }


    public void replace(int containerId, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commitAllowingStateLoss();
    }

    public void replaceHasHistory(int containerId, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.addToBackStack(null);//添加这个会在按返回键的时候先退出fragment，一些特殊场合可能用到，但效果其实不理想
        ft.commitAllowingStateLoss();
    }

    /**
     * 记录已添加的fragment
     */
    private ArrayList<Fragment> fs = new ArrayList<Fragment>();
    /**
     * 记录当前show的fragment
     */
    private Fragment currentFragment;

    /**
     * 添加fragment，这中方式的fragment需要用show来显示
     */
    public ArrayList<Fragment> add(int containerId, Fragment fragment) {
        if (!fs.contains(fragment)) {
            fs.add(fragment);
            fm.beginTransaction().add(containerId, fragment).commit();
        }
        return fs;
    }

    /**
     * 添加带tag的fragment
     */
    public void add(int containerId, Fragment fragment, String tag) {
        if (!fs.contains(fragment)) {
            fs.add(fragment);
            fm.beginTransaction().add(containerId, fragment, tag).commit();
        }
    }

    /**
     * 使用本方法前必须先add
     */
    public void show(Fragment fragment) {
        if (currentFragment == fragment) {
            return;//如果是当前fragment，则不重新show一遍了，无意义
        }
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fs) {
            ft.hide(f);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    /**
     * 移除add过的fragment
     */
    public void remove(Fragment fragment) {
        if (fs.contains(fragment)) {
            fs.remove(fragment);
            fm.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * 清空所有fragment
     */
    public void removeAll() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fs) {
            ft.remove(f);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 清空所有带此tag的fragment
     */
    public void removeAll(String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fs) {
            if (f.getTag().equals(tag)) ft.remove(f);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 清空所有除了带此tag的fragment
     */
    public void removeAllExcept(String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : fs) {
            if (f.getTag() != null && !f.getTag().equals(tag)) ft.remove(f);
        }
        ft.commitAllowingStateLoss();
    }

}

