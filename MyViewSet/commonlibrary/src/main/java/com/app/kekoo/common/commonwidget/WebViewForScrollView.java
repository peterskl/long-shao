package com.app.kekoo.common.commonwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by ${mms} on 2017/3/9.
 */

public class WebViewForScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;


    public WebViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)&& mGestureDetector.onTouchEvent(ev);
    }


    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                 return true;
            }
            return false;
        }
    }
}
