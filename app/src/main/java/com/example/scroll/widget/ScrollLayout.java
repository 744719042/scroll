package com.example.scroll.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ScrollLayout extends FrameLayout {
    private Scroller mScroller;
    private boolean mIsDragging;
    private int mDownY;
    private int mLastY;
    private int mTouchSlop;
    private VelocityTracker mTracker;

    public ScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTracker.addMovement(event);
        int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsDragging && Math.abs(y - mLastY) > mTouchSlop) {
                    mIsDragging = true;
                }

                if (mIsDragging) {
                    scrollBy(0, mLastY - y);
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragging = false;
                mTracker.computeCurrentVelocity(1000);
                float speed = mTracker.getYVelocity();
                if (Math.abs(speed) > 50) {
                    mScroller.fling(0, getScrollY(), 0, (int) -speed, 0, 0, -1000, 500);
                    postInvalidate();
                }
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
