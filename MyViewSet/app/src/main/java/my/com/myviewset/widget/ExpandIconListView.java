package my.com.myviewset.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import my.com.myviewset.R;
import my.com.myviewset.bean.ClassifyBean;
import my.com.utils.DrawUtils;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ExpandIconListView extends ViewGroup {

    private Context mContext;
    private int rowNum = 4;//4列
    private LayoutInflater mInflater;
    private int mItemWidth;
    private int mWidth;
    private int mMinimumHeight;
    private int mMaximumHeight;
    private int mDiffHeight;

    private View mTmpView;
    private ImageView mTmpIcon;
    private TextView mTmpTitle;
    private ClassifyBean mTmpBean;

    private String defaultExpandStr = "全部";
    private String defaultFoldStr = "收起";

    public ExpandIconListView(Context context) {
        this(context, null);
    }

    public ExpandIconListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandIconListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        setBackgroundColor(0xffffffff);
        initView();
    }

    private void initView() {
        mWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mItemWidth = mWidth / rowNum;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        if (count > 0) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            int currentLineNum = (count / rowNum) + ((count % rowNum) > 0 ? 1 : 0);

            //最大默认行数为3
            if (currentLineNum > 3) {
                currentLineNum = 3;
            }
            //最小高度
            mMinimumHeight = childHeight * currentLineNum;
            //最大高度
            mMaximumHeight = childHeight * ((count / rowNum) + (count % rowNum != 0 ? 1 : 0));

            mDiffHeight = mMaximumHeight - mMinimumHeight;
        }
        setMeasuredDimension(mWidth, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count > 0) {
            View child = getChildAt(0);
            int childHeight = child.getMeasuredHeight();
            int row = 0;
            for (int i = 0; i < count; i++) {
                child = getChildAt(i);
                child.setVisibility(VISIBLE);
                child.measure(mItemWidth, childHeight);
                if (i % 4 == 0 && i != 0) {
                    row++;
                }
                child.layout(i % rowNum * mItemWidth, row * childHeight, (i % rowNum + 1) * mItemWidth, (row + 1) * childHeight);
                if (i == count - 1) {
                    resetHeight();
                    if (mCallBack != null) {
                        mCallBack.onFinish();
                    }
                }
            }
        }
    }

    public void resetHeight() {
        LayoutParams layoutParams = getLayoutParams();
        if (mState == 1) {
            getLayoutParams().height = mMaximumHeight;
        } else {
            getLayoutParams().height = mMinimumHeight;
        }
        setLayoutParams(layoutParams);
        getParent().requestLayout();
    }

    public void setDataList(List<ClassifyBean> list) {
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            return;
        }
        removeAllViews();
        mTmpView = null;

        View itemView;
        TextView title;
        ImageView icon;
        ClassifyBean bean;
        String imageUrl;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, DrawUtils.dip2px(87));
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            itemView = LayoutInflater.from(mContext).inflate(R.layout.expand_icon_item_view, null);
            title = (TextView) itemView.findViewById(R.id.icon_title_tv);
            icon = (ImageView) itemView.findViewById(R.id.icon_iv);
            if (i == 11) {
                mTmpView = itemView;
                mTmpTitle = title;
                mTmpIcon = icon;
                mTmpBean = bean;
                mTmpTitle.setText(defaultExpandStr);
                mTmpIcon.setImageResource(R.mipmap.arrow_expand);
                mTmpView.setOnClickListener(new expandClickListener());
            } else {
                imageUrl = bean.getImg();
//                if (GlobalSettingUtils.getPersonal(mContext) == GlobalSettingUtils.PERSONAL_MALE) {
//                    if (!TextUtils.isEmpty(bean.getMaleImg())) {
//                        imageUrl = bean.getMaleImg();
//                    }
//                }
                title.setText(bean.getCategoryName());
                Glide.with(getContext()).load(imageUrl).into(icon);
                final ClassifyBean finalBean = bean;
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIconClickListener.onIconClick(finalBean);
                    }
                });
            }
            addView(itemView, layoutParams);
        }
        if (list.size() > 11) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.expand_icon_item_view, null);
            title = (TextView) itemView.findViewById(R.id.icon_title_tv);
            icon = (ImageView) itemView.findViewById(R.id.icon_iv);
            icon.setImageResource(R.mipmap.arrow_fold);
            title.setText(defaultFoldStr);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    foldView();
                }
            });
            addView(itemView, layoutParams);
        }
    }

    public void expandView() {
        mClickCallBack.onClick(1);
        setDefaultIconState(1);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                getLayoutParams().height = (int) (mMinimumHeight + animatedValue * mDiffHeight);
                getParent().requestLayout();
            }
        });
        valueAnimator.setDuration(250);
        valueAnimator.start();
    }

    public void foldView() {
        mClickCallBack.onClick(0);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                getLayoutParams().height = (int) (mMinimumHeight + animatedValue * mDiffHeight);
                getParent().requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setDefaultIconState(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(250);
        valueAnimator.start();
    }

    private int mState;

    public void setDefaultIconState(int state) {
        mState = state;
        if (state == 1) {
            mTmpTitle.setText(mTmpBean.getCategoryName());
            Glide.with(getContext()).load(mTmpBean.getImg()).into(mTmpIcon);
            mTmpView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIconClickListener.onIconClick(mTmpBean);
                }
            });
        } else {
            mTmpView.setOnClickListener(new expandClickListener());
            mTmpTitle.setText(defaultExpandStr);
            mTmpIcon.setImageResource(R.mipmap.arrow_expand);
        }
    }


    public int getMaxHeight() {
        return mMaximumHeight;
    }

    public int getMinHeight() {
        return mMinimumHeight;
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {
        void onFinish();
    }

    private OnIconClickListener mIconClickListener;

    public interface OnIconClickListener {
        void onIconClick(ClassifyBean bean);
    }

    public void setOnIconClickListener(OnIconClickListener iconClickListener) {
        mIconClickListener = iconClickListener;
    }

    private ClickCallBack mClickCallBack;

    public void setClickCallBack(ClickCallBack callBack) {
        mClickCallBack = callBack;
    }

    public interface ClickCallBack {
        /**
         * 1 展开 0 折叠
         *
         * @param state
         */
        void onClick(int state);
    }

    class expandClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            expandView();
        }
    }
}
