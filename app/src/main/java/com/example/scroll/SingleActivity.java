package com.example.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SingleActivity extends AppCompatActivity {
    private static final String TAG = "SingleActivity";

    private TextView text;
    private TextView status;
    private FrameLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        text = (TextView) findViewById(R.id.text);
        status = (TextView) findViewById(R.id.status);
        rootView = (FrameLayout) findViewById(R.id.rootView);
        text.setOnTouchListener(new View.OnTouchListener() {
            private int mLastX;
            private int mLastY;
            private int mDownX;
            private int mDownY;
            private int mTouchSlop = ViewConfiguration.get(SingleActivity.this).getScaledTouchSlop();
            private boolean mIsDragging = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX(), y = (int) event.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = mDownX = (int) event.getX();
                        mLastY = mDownY = (int) event.getY();
                        Log.d(TAG, "ACTION_DOWN: x = " + mLastX + ", y = " + mLastY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mIsDragging && (Math.abs(mDownX - x) > mTouchSlop || Math.abs(mDownY - y) > mTouchSlop)) {
                            Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);
                            mIsDragging = true;
                        }

                        Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);

                        if (mIsDragging) {
//                            text.setX(text.getX() + x - mLastX);
//                            text.setY(text.getY() + y - mLastY);

//                            text.offsetLeftAndRight(x - mLastX);
//                            text.offsetTopAndBottom(y - mLastY);

                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                            layoutParams.topMargin += y - mLastY;
                            layoutParams.leftMargin +=  x - mLastX;
                            text.requestLayout();
                        }

                        mLastX = x;
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP: x = " + mLastX + ", y = " + mLastY);
                        mIsDragging = false;
                        break;
                }
                status.setText(getString(R.string.location, (int) text.getX(), (int) text.getY()));
                return true;
            }
        });
    }
}
