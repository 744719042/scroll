package com.example.scroll.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.scroll.R;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SwipeLayout extends FrameLayout {
    private ViewDragHelper helper;
    private ViewGroup aboveView;
    private ViewGroup belowView;

    public SwipeLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        aboveView = (ViewGroup) findViewById(R.id.above_view);
        belowView = (ViewGroup) findViewById(R.id.below_view);
    }

    private void init() {
        helper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == aboveView;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (Math.abs(aboveView.getLeft()) < belowView.getWidth() / 2) {
                    helper.smoothSlideViewTo(aboveView, 0, aboveView.getTop());
                } else {
                    helper.smoothSlideViewTo(aboveView, -belowView.getWidth(), aboveView.getTop());
                }
                invalidate();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left < -belowView.getWidth()) {
                    return -belowView.getWidth();
                }

                if (left > 0) {
                    return 0;
                }

                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return aboveView.getTop();
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper != null && helper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }
}
