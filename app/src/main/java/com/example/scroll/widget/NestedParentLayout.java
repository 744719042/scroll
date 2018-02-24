package com.example.scroll.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.scroll.utils.CommonUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class NestedParentLayout extends LinearLayout implements NestedScrollingParent {
    private int mTopViewHeight;
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    public NestedParentLayout(@NonNull Context context) {
        this(context, null);
    }

    public NestedParentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedParentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTopViewHeight = CommonUtils.dp2px(150);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean isDownAndTopShow = dy > 0 && getScrollY() < mTopViewHeight;
        boolean isUpAndTopShow = dy < 0 && getScaleY() > 0 && ViewCompat.canScrollVertically(target, -1);

        if (isDownAndTopShow || isUpAndTopShow) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }


    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    public void onStopNestedScroll(View target) {
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }
}
