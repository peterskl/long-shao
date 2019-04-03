package my.com.myviewset.widget.newlineitem;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.app.kekoo.common.commonutils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator.
 */

public class NewlineLayout extends ViewGroup {

    private int rowNum = 3;//4列
    private int mItemWidth;
    private int mWidth;
    private int mHeight;

    private int mItemMargin = DisplayUtil.dip2px(10); //item之间的间隔
    private int mLineMargin = DisplayUtil.dip2px(5); //行间距
    private List<NewLineItemView> mItemViews;
    private List<String> mCurrentlist = new ArrayList<>();

    public NewlineLayout(Context context) {
        this(context, null);
    }

    public NewlineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewlineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        if (count > 0) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            int currentLineNum = (count / rowNum) + ((count % rowNum) != 0 ? 1 : 0);
            //layout高度
            mHeight = childHeight * currentLineNum + (mLineMargin * (currentLineNum - 1));
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            mItemWidth = (mWidth - mItemMargin * (rowNum - 1)) / rowNum;//计算每个item的宽度
        }
        setMeasuredDimension(mWidth, mHeight);
        getPivotX();
        getX();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count > 0) {
            View child = getChildAt(0);
            int childHeight = child.getMeasuredHeight();
            int row = 0;
            int left, top, right, bottom;
            int marginCount = 0;
            for (int i = 0; i < count; i++) {
                child = getChildAt(i);
                child.setVisibility(VISIBLE);
                child.measure(mItemWidth, childHeight);
                if (i % rowNum == 0 && i != 0) {
                    row++;
                }
                if (i % rowNum != 0) {
                    marginCount++;
                } else {
                    marginCount = 0;
                }

                left = i % rowNum * mItemWidth + (mItemMargin * marginCount);
                right = (i % rowNum + 1) * mItemWidth + (mItemMargin * marginCount);
                top = row * childHeight + (mLineMargin * row);
                bottom = (row + 1) * childHeight + (mLineMargin * row);

                child.layout(left, top, right, bottom);
            }
        }
    }

    public void setDataList(List<String> list, boolean isNeedAddIcon) {
        if (!isNeedAddIcon) {
            if (list == null) {
                return;
            }
            if (list.size() == 0) {
                return;
            }
        } else {
            if (list == null) {
                list = new ArrayList<>();
            }
        }

        mCurrentlist.clear();
        mCurrentlist.addAll(list);
        removeAllViews();
        mItemViews = new ArrayList<>();
        mItemViews.clear();

        if (isNeedAddIcon) {
            mCurrentlist.add("+");
        }

        NewLineItemView itemView;
        String titleStr;
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(DisplayUtil.sp2px(14));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(35));
        for (int i = 0; i < mCurrentlist.size(); i++) {
            titleStr = mCurrentlist.get(i);
            itemView = new NewLineItemView(getContext());
            itemView.setText(titleStr);
            addView(itemView, layoutParams);
            mItemViews.add(itemView);
            if (isNeedAddIcon && i == mCurrentlist.size() - 1) {
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickCallBack != null) {
                            mClickCallBack.onAddIconClick();
                        }
                    }
                });
            }
        }
    }

    public void clearAllState(){
        for (NewLineItemView view : mItemViews) {
            if (view.isSelect()) {
                view.changeState(false);
            }
        }
    }

    public List<String> getSelectList() {
        List<String> selectList = new ArrayList<>();
        if (mItemViews!=null && mItemViews.size()>0) {
            for (NewLineItemView view : mItemViews) {
                if (view.isSelect()) {
                    selectList.add(view.getText());
                }
            }
        }
        return selectList;
    }

    private ClickCallBack mClickCallBack;

    public void setClickCallBack(ClickCallBack callBack) {
        mClickCallBack = callBack;
    }

    public interface ClickCallBack {
        void onAddIconClick();
    }

}
