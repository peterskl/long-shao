package com.app.kekoo.common.baserx;

import android.content.Context;
import android.content.DialogInterface;

import com.app.kekoo.common.R;
import com.app.kekoo.common.baseapp.BaseApplication;
import com.app.kekoo.common.commonutils.NetWorkUtils;
import com.app.kekoo.common.commonwidget.LoadingDialog;

import rx.Subscriber;

/**
 * des:订阅封装
 * Created by mms
 * on 2016.09.10:16
 */


public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDialog loadingDialog;



    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
        loadingDialog = new LoadingDialog(mContext, msg, true);


    }

    public RxSubscriber(Context context) {
        this(context, "", true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            loadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            loadingDialog.showDialogForLoading().setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    unsubscribe();
                }
            });

        }
    }


    @Override
    public void onNext(T t) {

        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog)
            loadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
