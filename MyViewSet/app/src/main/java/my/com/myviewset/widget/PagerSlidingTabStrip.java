/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.com.myviewset.widget;

/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import my.com.myviewset.R;
import my.com.utils.DrawUtils;

import static android.R.attr.textSize;


@SuppressLint("NewApi")
public class PagerSlidingTabStrip extends ObservableHorizontalScrollView {

    public interface IconTabProvider {
        public int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{textSize,
            android.R.attr.textColor};
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public ViewPager.OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private int selectedPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;
    private Paint selectPaintRect;

    private int indicatorColor = 0xFF696969;
    private int underlineColor = 0x00000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = false;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabPadding = 3;
    private float dividerWidth = 0f;
    private int underLineMarginBottom = 0;

    private int tabTextSize = 14;
    private int tabTextColor = 0xFFFFFFFF; //在使用时，一定要加上透明度
    private int selectedTabTextColor = 0xFFFFFFFF; //在使用时，一定要加上透明度
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int tabTypefaceStyleSelected = Typeface.NORMAL;

    private int lastScrollX = 0;

    private int tabBackgroundResId = R.drawable.background_tab;

    private Locale locale;

    private ViewPagerStateListener viewpagerStateListener;

    private boolean isShouldLineExpand = true;  //底部的线条在文字match的时候是否也match
    private boolean isNeedShortIndicatorLine = false;  //底部滑动的线是不是短点
    private boolean indicatorIsRoundRect = false;  //底部滑动的线是不是圆角

    private boolean isShowRoundBg = false; //显示

    private RectF mTempRectF = new RectF();

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setIsShowRoundBg(boolean isShow) {
        isShowRoundBg = isShow;
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = a.getColor(1, tabTextColor);

        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs,
                R.styleable.PagerSlidingTabStrip);

        indicatorColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsIndicatorColor,
                indicatorColor);
        underlineColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsUnderlineColor,
                underlineColor);
        dividerColor = a
                .getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor,
                        dividerColor);
        indicatorHeight = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight,
                indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight,
                underlineHeight);
        dividerPadding = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsDividerPadding,
                dividerPadding);
        tabPadding = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight,
                tabPadding);
        tabBackgroundResId = a.getResourceId(
                R.styleable.PagerSlidingTabStrip_pstsTabBackground,
                tabBackgroundResId);
        shouldExpand = a
                .getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand,
                        shouldExpand);
        scrollOffset = a
                .getDimensionPixelSize(
                        R.styleable.PagerSlidingTabStrip_pstsScrollOffset,
                        scrollOffset);
        textAllCaps = a.getBoolean(
                R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);

        selectPaintRect = new Paint();
        selectPaintRect.setAntiAlias(true);
        selectPaintRect.setStyle(Paint.Style.FILL);
        selectPaintRect.setColor(0xFFFFCB11);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0,
                FrameLayout.LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");

        }
        pager.addOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i,
                        ((IconTabProvider) pager.getAdapter())
                                .getPageIconResId(i));
            } else {
                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }
        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        currentPosition = pager.getCurrentItem();
                        scrollToChild(currentPosition, 0);
                    }
                });

    }

    @SuppressWarnings("deprecation")
    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setPadding(0, 0, 0, 0);
        tab.setBackgroundColor(Color.RED);

        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        TextPaint textPaint = tab.getPaint();
        if ((textPaint.measureText(title) + tabPadding * 2) * tabCount < wm.getDefaultDisplay().getWidth()) {
            shouldExpand = true;//如果所有tab的宽度小于屏幕宽度 那就让它们平分屏幕宽度
        }
        if (shouldExpand) {
            int width = wm.getDefaultDisplay().getWidth();
            tab.setMaxWidth(width / tabCount);
        }
//        Log.e("cpt", "*/**/***/*/*/*/**/*/ 屏幕宽=" + wm.getDefaultDisplay().getWidth() + " count个数=" + tabCount);
//        Log.e("cpt", "*/**/***/*/*/*/**/*/ " + title + ":" + textPaint.measureText(title) + " tabPadding=" + tabPadding);
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setSingleLine();
        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewpagerStateListener != null) {
                    viewpagerStateListener.onPageScrollStateChanged(1);
                }

                pager.setCurrentItem(position);
            }
        });

        tab.setPadding(tabPadding, 0, tabPadding, 0);


        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams
                : defaultTabLayoutParams);

    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);

            v.setBackgroundResource(tabBackgroundResId);

            if (v instanceof TextView) {

                TextView tab = (TextView) v;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);

                if (i == selectedPosition) {
                    tab.setTextColor(selectedTabTextColor);
                    tab.setTypeface(tabTypeface, tabTypefaceStyleSelected);
                } else {
                    tab.setTextColor(tabTextColor);
                    tab.setTypeface(tabTypeface, tabTypefaceStyle);
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        if (isShowRoundBg) {
            drawRoundRectBg(canvas);
        }
        final int height = getHeight() - underLineMarginBottom;

        // draw underline
        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(),
                height, rectPaint);

        // draw indicator line
        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        TextView currentTab = (TextView) tabsContainer.getChildAt(currentPosition);
        float lineLeft, lineRight;
        if (isNeedShortIndicatorLine) {
            lineLeft = currentTab.getLeft() + currentTab.getPaddingLeft();
            lineRight = currentTab.getRight() - currentTab.getPaddingRight();
        } else {
            lineLeft = currentTab.getLeft() + currentTab.getPaddingLeft() - DrawUtils.dip2px(5);
            lineRight = currentTab.getRight() - currentTab.getPaddingRight() + DrawUtils.dip2px(5);
        }


        //TODO:1.12增加
        float textWidth = 0;
        if (!isShouldLineExpand) {
            textWidth = currentTab.getPaint().measureText(currentTab.getText().toString());

            lineLeft = (getWidth() / tabCount - textWidth) / 2 + getWidth() / tabCount * currentPosition;
            lineRight = (getWidth() / tabCount + textWidth) / 2 + getWidth() / tabCount * currentPosition;
        }

        // if there is an offset, start interpolating left and right coordinates
        // between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            float nextTabLeft = nextTab.getLeft() + currentTab.getPaddingLeft() - DrawUtils.dip2px(5);
            float nextTabRight = nextTab.getRight() - currentTab.getPaddingRight() + DrawUtils.dip2px(5);
            if (!isShouldLineExpand) {
                textWidth = currentTab.getPaint().measureText(currentTab.getText().toString());

                nextTabLeft = (getWidth() / tabCount - textWidth) / 2 + getWidth() / tabCount * currentPosition;
                nextTabRight = (getWidth() / tabCount + textWidth) / 2 + getWidth() / tabCount * currentPosition;
            }

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset)
                    * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset)
                    * lineRight);
        }

        if (!isShowRoundBg) {
            if (indicatorIsRoundRect) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawRoundRect(lineLeft, height - indicatorHeight + underLineMarginBottom, lineRight, height
                            + underLineMarginBottom, height / 2, height / 2, rectPaint);
                } else {
                    canvas.drawRect(lineLeft, height - indicatorHeight + underLineMarginBottom, lineRight, height
                            + underLineMarginBottom, rectPaint);
                }
            } else {
                canvas.drawRect(lineLeft, height - indicatorHeight + underLineMarginBottom, lineRight, height
                        + underLineMarginBottom, rectPaint);
            }
        }


        // draw divider

//        dividerPaint.setColor(dividerColor);
//        for (int i = 0; i < tabCount - 1; i++) {
//            View tab = tabsContainer.getChildAt(i);
//            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(),
//                    height - dividerPadding, dividerPaint);
//        }
    }

    int topPadding = DrawUtils.dip2px(2);
    int leftPadding = DrawUtils.dip2px(2);

    //绘制选中的圆角背景颜色
    private void drawRoundRectBg(Canvas canvas) {
        final int height = getHeight() - 2 * topPadding;
        final int width = getWidth() - 2 * leftPadding;
        int itemWidth = width / tabCount;
        int left = currentPosition * itemWidth + leftPadding;
        mTempRectF.set(left, topPadding, left + itemWidth, topPadding + height);
        canvas.drawRoundRect(mTempRectF, height / 2, height / 2, selectPaintRect);
    }

    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer
                    .getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }


            if (viewpagerStateListener != null) {
                viewpagerStateListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            selectedPosition = position;
            updateTabStyles();
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }

    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        notifyDataSetChanged();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setUnderLineMarginBottom(int underLineMarginBottom) {
        this.underLineMarginBottom = underLineMarginBottom;
    }

    public int getUnderLineMarginBottom() {
        return this.underLineMarginBottom;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    /**
     * 娌￠?涓��瀛���?
     */
    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setSelectedTextColor(int textColor) {
        this.selectedTabTextColor = textColor;
        updateTabStyles();
    }

    public void setSelectedTextColorResource(int resId) {
        this.selectedTabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getSelectedTextColor() {
        return selectedTabTextColor;
    }

    public void setTypeface(Typeface typeface, int style, int selectedStyle) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        this.tabTypefaceStyleSelected = selectedStyle;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
        updateTabStyles();
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    public boolean isShouldLineExpand() {
        return isShouldLineExpand;
    }

    public void setShouldLineExpand(boolean shouldLineExpand) {
        isShouldLineExpand = shouldLineExpand;
    }

    public void setIsNeedShortIndicatorLine(boolean needShortIndicatorLine) {
        isNeedShortIndicatorLine = needShortIndicatorLine;
    }

    public void setIndicatorIsRoundRect(boolean indicatorIsRoundRect) {
        this.indicatorIsRoundRect = indicatorIsRoundRect;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends View.BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };


    }

    public void setViewPagerStateListener(ViewPagerStateListener viewpagerStateListener) {
        this.viewpagerStateListener = viewpagerStateListener;
    }

    public interface ViewPagerStateListener {
        public void onPageScrollStateChanged(int state);
    }
}

