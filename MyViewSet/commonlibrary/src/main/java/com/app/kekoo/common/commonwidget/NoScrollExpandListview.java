package com.app.kekoo.common.commonwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2017-2-9.
 */

public class NoScrollExpandListview extends ExpandableListView {
    public NoScrollExpandListview(Context context) {
        super(context);
    }

    public NoScrollExpandListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollExpandListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
