package com.app.kekoo.common.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.app.kekoo.common.R;
import com.app.kekoo.common.commonutils.DisplayUtil;

import butterknife.ButterKnife;


public abstract class BaseBottomDialog extends AppCompatDialogFragment {

	protected final static float ALPHA = 1f; // 完全不透明
	//初始化view
	public abstract int getLayoutId();
	public abstract void initView();
	public Context mContext;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final Window window = getDialog().getWindow();
		View view = inflater.inflate(getLayoutId(),  ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
		window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;
		window.setWindowAnimations(R.style.BottomToTopAnim);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.gravity = Gravity.BOTTOM; // 紧贴底部
		lp.width = DisplayUtil.getScreenWidth(getContext()); // 宽度适应屏幕
		window.setAttributes(lp);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}


	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
