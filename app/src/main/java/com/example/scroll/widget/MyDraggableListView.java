package com.example.scroll.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.scroll.R;
import com.example.scroll.adapter.GestureAdapter;
import com.example.scroll.adapter.UserListAdapter;
import com.example.scroll.utils.CommonUtils;

import java.util.Collections;

import static com.example.scroll.widget.MyDraggableListView.Direct.DOWN;
import static com.example.scroll.widget.MyDraggableListView.Direct.NONE;
import static com.example.scroll.widget.MyDraggableListView.Direct.UP;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MyDraggableListView extends ListView {
    private static final String TAG = "MyDraggableListView";
    private Bitmap shadow;
    private Canvas canvas;
    private int downX;
    private int downY;
    private int lastX;
    private int lastY;
    private WindowManager windowManager;
    private GestureDetector detector;
    private Rect bound;
    private int dragPos;
    private int dataPos;
    private boolean isLongPressed = false;
    private ImageView shadowView;
    private WindowManager.LayoutParams params;
    private int scrollState = OnScrollListener.SCROLL_STATE_IDLE;
    private boolean manualCancel = false;
//    private Runnable scroll = new Runnable() {
//        @Override
//        public void run() {
//            scrollList();
//        }
//    };
    enum Direct {
        NONE, UP, DOWN;
    }
    public MyDraggableListView(Context context) {
        this(context, null);
    }

    public MyDraggableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDraggableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        detector = new GestureDetector(getContext(), new GestureAdapter() {
            @Override
            public void onLongPress(MotionEvent e) {
                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                isLongPressed = true;
                View child = getChildAt(dragPos);
                if (shadow == null) {
                    shadow = Bitmap.createBitmap(child.getWidth(), child.getHeight(), Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(shadow);
                    shadowView = new ImageView(getContext());
                }

                child.draw(canvas);
                shadowView.setImageBitmap(shadow);
                int[] locations = new int[2];
                child.getLocationInWindow(locations);
                addShadowView(shadow, locations[0], locations[1]);
            }
        });
        params = new WindowManager.LayoutParams();
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int state) {
                Log.d(TAG, "state " + state);
                scrollState = state;
//                if (scrollState == SCROLL_STATE_IDLE && testScroll() != Direct.NONE && isLongPressed) {
//                    removeCallbacks(scroll);
//                    post(scroll);
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void addShadowView(Bitmap shadow, int x, int y) {
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.width = shadow.getWidth();
        params.height = shadow.getHeight();
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        params.x = x;
        params.y = y + CommonUtils.dp2px(10);

        windowManager.addView(shadowView, params);
    }

    private void updateShadowView(int dx, int dy) {
        params.x += dx;
        params.y = Math.max(bound.top, params.y + dy);
        windowManager.updateViewLayout(shadowView, params);
    }

    private void removeShadowView() {
        try {
            windowManager.removeView(shadowView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (bound == null) {
            int[] locations = new int[2];
            getLocationInWindow(locations);
            bound = new Rect(locations[0], locations[1],
                    locations[0] + getWidth(), locations[1] + getHeight());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX(), y = (int) ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = downX = x;
                lastY = downY = y;
                dataPos = pointToPosition(x, y);
                dragPos = pointToPosition(x, y) - getFirstVisiblePosition();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongPressed) {
                    updateShadowView(x - lastX, y - lastY);
                    if (scrollState != OnScrollListener.SCROLL_STATE_IDLE && !shouldScrollDown() && !shouldScrollUp()) {
                        stop();
                    }
                    lastX = x;
                    lastY = y;
                    if (pointToPosition(x, y) == INVALID_POSITION) {
                        for (int i = 0, count = getChildCount(); i < count; i++) {
                            getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                        return true;
                    }
                    int pos = pointToPosition(x, y) - getFirstVisiblePosition();
                    if (testScroll() == NONE) {
                        for (int i = 0, count = getChildCount(); i < count; i++) {
                            if (pos != dragPos && i == pos) {
                                getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                getChildAt(i).setBackgroundColor(Color.WHITE);
                            }
                        }
                    } else {
                        for (int i = 0, count = getChildCount(); i < count; i++) {
                            getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                    }

                    scrollList();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (manualCancel) {
                    manualCancel = false;
                    super.onTouchEvent(ev);
                    return true;
                }
            case MotionEvent.ACTION_UP:
                removeShadowView();
                if (isLongPressed) {
                    isLongPressed = false;
                    int targetPos = pointToPosition(x, y);
                    if (targetPos == INVALID_POSITION) {
                        for (int i = 0, count = getChildCount(); i < count; i++) {
                            getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                        return true;
                    }
                    int pos = pointToPosition(x, y) - getFirstVisiblePosition();
                    getChildAt(pos).setBackgroundColor(Color.WHITE);
                    if (targetPos != dataPos) {
                        Collections.swap(UserListAdapter.users, targetPos, dataPos);
                        ((BaseAdapter) getAdapter()).notifyDataSetChanged();
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private Direct testScroll() {
        if (shouldScrollDown()) {
            return DOWN;
        } else if (shouldScrollUp()) {
            return UP;
        }

        return NONE;
    }

    private boolean shouldScrollUp() {
        return params.y <= bound.top && (getFirstVisiblePosition() != 0 ||
                getFirstVisiblePosition() == 0 && getChildAt(0).getTop() != getPaddingTop());
    }

    private boolean shouldScrollDown() {
        return params.y >= bound.bottom - params.height && (getLastVisiblePosition() != getAdapter().getCount() - 1 ||
                getLastVisiblePosition() == getAdapter().getCount() - 1 &&
                        getChildAt(getChildCount() - 1).getBottom() != getHeight() - getPaddingBottom());
    }

    private void scrollList() {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (shouldScrollDown()) {
                smoothScrollBy(bound.height(), bound.height() / 100 * 1000);
            } else if (shouldScrollUp()) {
                smoothScrollBy(-bound.height(), bound.height() / 100 * 1000);
            }
        }
    }

    public void stop() {
        manualCancel = true;
        dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_CANCEL, 0, 0, 0));
    }


}
