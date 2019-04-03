package com.app.kekoo.common.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.app.kekoo.common.R;
import com.app.kekoo.common.baseapp.AppManager;
import com.app.kekoo.common.baserx.RxManager;
import com.app.kekoo.common.commonutils.TUtil;
import com.app.kekoo.common.commonutils.ToastUitl;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;


public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    protected ImmersionBar mImmersionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        hideNavigationBar();

        mRxManager = new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        this.initPresenter();
        this.initView();
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
    }


    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        //设置昼夜主题
//        initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();


    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
//        toolbar_title.setText(title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled) {
        toolbar.setTitle("");
//        toolbar_title.setText(title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
//        LoadingDialog.showDialogForLoading(this);
    }


    /**
     * 带图片的toast
     *
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text, res);
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
//        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(),R.drawable.ic_wifi_off);
    }

    public void showNetErrorTip(String error) {
//        ToastUitl.showToastWithImg(error, R.drawable.ic_wifi_off);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.tran_pre_enter, R.anim.tran_pre_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }
}
