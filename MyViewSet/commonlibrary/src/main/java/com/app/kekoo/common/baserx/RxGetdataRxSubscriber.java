package com.app.kekoo.common.baserx;

import android.content.Context;
import android.content.DialogInterface;

import com.app.kekoo.common.R;
import com.app.kekoo.common.baseapp.BaseApplication;
import com.app.kekoo.common.basebean.BaseRespose;
import com.app.kekoo.common.commonutils.NetWorkUtils;
import com.app.kekoo.common.commonutils.ToastUitl;
import com.app.kekoo.common.commonwidget.LoadingDialog;

import rx.Subscriber;

/**
 * Created by ${mms} on 2016/12/10.
 * 此类用于获取数据流里面的getdata,隔离BaseRespose，加载进度条并发出toast
 */

public abstract class RxGetdataRxSubscriber<T extends BaseRespose> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDialog loadingDialog;
    private boolean isShowToast = true;


    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    /**
     * @param context
     * @param msg        dialog 加载文字
     * @param showDialog 是否加载dialog，默认加载
     */
    public RxGetdataRxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
        loadingDialog = new LoadingDialog(mContext, msg, true);


    }

    /**
     * @param context
     */
    public RxGetdataRxSubscriber(Context context) {
        this(context, "", true);
    }

    /**
     * @param context
     */
    public RxGetdataRxSubscriber(Context context, String msg) {
        this(context, "", true);
    }

    /**
     * @param context
     * @param showDialog 显示dialog，默认系统的<string name="loading">Loading...</string>
     */
    public RxGetdataRxSubscriber(Context context, boolean showDialog) {
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

        if (t.getRet() == 200) {
            _onNext(t);
            getDate(t.getData());

        } else {
            _onError(t.getData().toString());
        }

        switch (t.getRet()) {
            case 200:
                getDate(t.getData());
                break;
            case 202:
                ToastUitl.show("还没有订阅项目所属公司",0);
                break;
        }

    }

//    private void loginback() {
//        if (!TextUtils.isEmpty(SPUtils.get(AppApplication.getAppContext(), SPUtils.ACCOUNT, "").toString()) && !TextUtils.isEmpty(SPUtils.get(AppApplication.getAppContext(), SPUtils.PASSWORD, "").toString())) {
//            Api.getDefault().login((String) SPUtils.get(AppApplication.getAppContext(), SPUtils.ACCOUNT, ""), Md5.MD5((String) SPUtils.get(AppApplication.getAppContext(), SPUtils.PASSWORD, ""))).compose(RxSchedulers.<BaseRespose<LoginBean>>io_main()).subscribe(new Subscriber<BaseRespose<LoginBean>>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(BaseRespose<LoginBean> loginBeanBaseRespose) {
//
//
//                }
//            });
//        }
//
//
//    }

    @Override
    public void onError(Throwable e) {
        if (showDialog)
            loadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            ToastUitl.showShort(BaseApplication.getAppContext().getString(R.string.no_net));
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            ToastUitl.showShort(e.getMessage());
            _onError(e.getMessage());
        }
        //其它
        else {
            ToastUitl.showShort(e.getMessage());
            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    public void _onError(String message) {

    }


    public void getDate(Object data) {

    }


}
