package com.example.scroll;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DragAndDropActivity extends AppCompatActivity {
    private static final String TAG = "DragAndDropActivity";
    private ImageView imageView;
    private FrameLayout target1;
    private FrameLayout target2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);
        imageView = (ImageView) findViewById(R.id.image);
        target1 = (FrameLayout) findViewById(R.id.target1);
        target2 = (FrameLayout) findViewById(R.id.target2);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(TAG);
                ClipData clipData = new ClipData(TAG, new String[] {
                        ClipDescription.MIMETYPE_TEXT_PLAIN
                }, item);
                imageView.startDrag(clipData, new View.DragShadowBuilder(imageView), null, 0);
                return true;
            }
        });

        target1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(TAG, "target1: action = ACTION_DRAG_STARTED");
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(TAG, "target1: action = ACTION_DRAG_ENTERED");
                        target1.setBackgroundColor(Color.GREEN);
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(TAG, "target1: action = ACTION_DRAG_LOCATION");
                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.d(TAG, "target1: action = ACTION_DROP");
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(TAG, "target1: action = ACTION_DRAG_EXITED");
                        target1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(TAG, "target1: action = ACTION_DRAG_ENDED");
                        return true;
                }
                return false;
            }
        });

        target2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(TAG, "target2: action = ACTION_DRAG_STARTED");
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            return true;
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(TAG, "target2: action = ACTION_DRAG_ENTERED");
                        target2.setBackgroundColor(Color.GREEN);
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(TAG, "target2: action = ACTION_DRAG_LOCATION");
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(TAG, "target2: action = ACTION_DRAG_EXITED");
                        target2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.d(TAG, "target2: action = ACTION_DROP");
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(TAG, "target2: action = ACTION_DRAG_ENDED");
                        return true;
                }
                return false;
            }
        });
    }
}
