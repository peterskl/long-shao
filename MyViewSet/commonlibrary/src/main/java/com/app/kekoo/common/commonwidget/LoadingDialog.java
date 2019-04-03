package com.app.kekoo.common.commonwidget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.kekoo.common.R;


/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDialog implements DialogInterface.OnCancelListener {
    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;
    private Context context;
    private String mag = "";
    private boolean cancelable = true;
    private ImageView iv_loadview;
    private AnimationDrawable animationDrawable;

    public LoadingDialog(Context context, String mag, boolean cancelable) {
        this.context = context;
        this.mag = mag;
        this.cancelable = cancelable;
    }

    public LoadingDialog(Context context, boolean cancelable) {
        this.context = context;
        this.mag = mag;
        this.cancelable = cancelable;
    }

    /**
     * 显示加载对话框
     */
    public Dialog showDialogForLoading() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.tv_loadview_msg);
        loadingText.setText(mag);

        iv_loadview = (ImageView) view.findViewById(R.id.iv_loadview);
//        iv_loadview.setImageResource(R.drawable.dialog_loadview);
//        animationDrawable = (AnimationDrawable) iv_loadview.getDrawable();
//        animationDrawable.start();

        iv_loadview = (ImageView) view.findViewById(R.id.iv_loadview);


        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setOnCancelListener(this);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }


    /**
     * 关闭加载对话框
     */
    public void cancelDialogForLoading() {
        if (null != animationDrawable) {

            animationDrawable.stop();
        }
        if (mLoadingDialog != null) {

            mLoadingDialog.cancel();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (null != animationDrawable) {

            animationDrawable.stop();
        }
    }
}
