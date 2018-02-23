package com.example.scroll.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scroll.utils.CommonUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class DraggerLayout extends FrameLayout {
    private static final String TAG = "DraggerLayout";

    private ViewDragHelper helper;

    public DraggerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DraggerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child instanceof TextView) {
                    if (((TextView) child).getText().toString().contains("移动")) {
                        return true;
                    }
                }
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d(TAG, "left = " + left + ", dx = " + dx);
                if (child instanceof LinearLayout && left > 0) {
                    return 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.d(TAG, "top = " + top + ", dy = " + dy);
                if (child instanceof LinearLayout) {
                    return 0;
                }
                return top;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                helper.captureChildView(getChildAt(getChildCount() - 1), pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild instanceof LinearLayout) {
                    int left = Math.abs(releasedChild.getLeft());
                    if (left < CommonUtils.dp2px(125)) {
                        helper.smoothSlideViewTo(releasedChild, 0, 0);
                    } else {
                        helper.smoothSlideViewTo(releasedChild, -CommonUtils.dp2px(250), 0);
                    }
                    invalidate();
                }
            }
        });
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
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
