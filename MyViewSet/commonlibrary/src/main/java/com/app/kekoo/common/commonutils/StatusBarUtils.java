package com.app.kekoo.common.commonutils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by mingshanmo on 2017/7/10.
 */

public class StatusBarUtils {

    public static final String MOBILE_HUAWEI = "HUAWEI";

    public static int statusBarHeight;
    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏要设置的颜色值
     */
    public static View setColor(Activity activity, int color) {
        View statusView = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !MOBILE_HUAWEI.equals(android.os.Build.BRAND)) {
            //5.0及以上
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
        return statusView;
    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity){
        if(statusBarHeight <= 0){
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 生成一个和状态栏大小相同的矩形条 *
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏 view
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    /**
     * 使状态栏透明 <br/>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent_Lollipop(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //5.0及以上
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            //false在状态栏绘制，true不在状态栏绘制
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 设置状态栏透明，4.4~5.0，（增加过状态栏view，将先移除view）
     *
     * @param activity   activity
     * @param statusView 之前增加的状态栏view
     */
    public static void setTranslucent(Activity activity, View statusView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 4.4~5.0 设置状态栏透明，先移除之前的statusView
            if (statusView != null) {
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                decorView.removeView(statusView);
            }
        }
        setTranslucent_Lollipop(activity);
    }

    /**
     * 直接设置状态栏透明，将顶部view延伸到状态栏 <br/>
     * 将顶部view paddingTop状态栏高度  <br/>
     * 顶部view需要有颜色背景
     *
     * @param topView 界面顶部view
     */
    public static void setStatusTranslucent(Activity activity, View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            //false在状态栏绘制，true不在状态栏绘制
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            topView.setPadding(0, statusBarHeight, 0, 0);
        }
    }


    public static boolean isLoginByTeacher(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("dy_user", 0);
        return preferences.getBoolean("isLoginByTeacher", false);
    }


//    public static void setTitleColor(View view, Context context) {
//        //setTitleColor
//        if (StatusBarUtils.isLoginByTeacher(context)) {
//            view.setBackgroundColor(context.getResources().getColor(R.color.color_blue_topBar));
//        } else {
//            view.setBackgroundColor(context.getResources().getColor(R.color.color_green_topBar));
//        }
//    }

}
