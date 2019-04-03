package com.app.kekoo.common.commonutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

/**
 * @author mms supernan 屏幕适配工具
 */
public class DensityUtil {
	/**
	 * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		return width;
	}

	public static int getHeiht(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();// 屏幕高度
		return height;
	}

	public static int getState(Activity context) {
		Rect rect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top; // 状�?栏高�?
		return statusBarHeight;
	}

	public static int getAPPHeight(Activity activity) {
		WindowManager wm = (WindowManager) activity
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();// 屏幕高度
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top; // 状�?栏高�?
		return height - statusBarHeight;
	}

}
