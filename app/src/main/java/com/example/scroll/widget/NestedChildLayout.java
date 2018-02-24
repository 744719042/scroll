package com.example.scroll.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/2/23.
 */

public class NestedChildLayout extends ViewGroup implements NestedScrollingChild {

    public NestedChildLayout(Context context) {
        this(context, null);
    }

    public NestedChildLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


}
