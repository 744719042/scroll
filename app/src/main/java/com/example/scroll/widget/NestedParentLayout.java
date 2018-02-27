package com.example.scroll.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.scroll.R;
import com.example.scroll.utils.CommonUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class NestedParentLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "NestedParentLayout";
    private ViewPager viewPager;
    private Scroller mScroller;

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
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        viewPager.getLayoutParams().height = height - CommonUtils.dp2px(45);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        ListView listView = (ListView) target;
        boolean isDownAndTopShow = dy > 0 && getScrollY() <= mTopViewHeight &&
                listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == listView.getPaddingTop();
        boolean isUpAndTopHide = dy < 0 && getScrollY() >= 0 && getScrollY() < mTopViewHeight;
        Log.d(TAG, "dy = " + dy + ", isDownAndTopShow = " + isDownAndTopShow + ", isUpAndTopHide = " + isUpAndTopHide);

        if (isDownAndTopShow || isUpAndTopHide) {
            scrollBy(0, -dy);
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
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }
}
