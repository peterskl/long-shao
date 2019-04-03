package com.app.kekoo.common.base;

import android.app.Activity;
import android.content.Context;

import com.app.kekoo.common.baserx.RxManager;


/**
 * des:基类presenter
 * Created by xsf
 * on 2016.07.11:55
 */
public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m, Activity mContext) {
        this.mContext = mContext;
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {

    }


    public void onDestroy() {
        mRxManage.clear();
    }


}
