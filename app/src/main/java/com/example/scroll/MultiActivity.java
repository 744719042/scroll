package com.example.scroll;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MultiActivity extends AppCompatActivity {
    private static final String TAG = "MultiActivity";

    private TextView text;
    private TextView text2;
    private TextView status;
    private FrameLayout rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        text = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
        status = (TextView) findViewById(R.id.status);
        rootView = (FrameLayout) findViewById(R.id.rootView);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            private int mLastX;
            private int mLastY;
            private int mDownX;
            private int mDownY;
            private int mTouchSlop = ViewConfiguration.get(MultiActivity.this).getScaledTouchSlop();
            private boolean mIsDragging = false;
            private Rect mTmpRect = new Rect();
            private View mTargetView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX(), y = (int) event.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = mDownX = (int) event.getX();
                        mLastY = mDownY = (int) event.getY();
                        Log.d(TAG, "ACTION_DOWN: x = " + mLastX + ", y = " + mLastY);
                        decideTargetView(x, y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mIsDragging && (Math.abs(mDownX - x) > mTouchSlop || Math.abs(mDownY - y) > mTouchSlop)) {
                            Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);
                            mIsDragging = true;
                        }

                        Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);

                        if (mIsDragging && mTargetView != null) {
//                            text.setX(text.getX() + x - mLastX);
//                            text.setY(text.getY() + y - mLastY);

                            int dx = x - mLastX, dy = y - mLastY;
                            mTargetView.offsetLeftAndRight(dx);
                            mTargetView.offsetTopAndBottom(dy);
                        }

                        mLastX = x;
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP: x = " + mLastX + ", y = " + mLastY);
                        mIsDragging = false;
                        mTargetView = null;
                        break;
                }
                status.setText(getString(R.string.location, (int) text.getX(), (int) text.getY()));
                return true;
            }

            private void decideTargetView(int x, int y) {
                int count = rootView.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = rootView.getChildAt(i);
                    mTmpRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    if (mTmpRect.contains(x, y)) {
                        mTargetView = view;
                        break;
                    }
                }
            }
        });
    }
}
