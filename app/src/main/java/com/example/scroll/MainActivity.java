package com.example.scroll;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int OVERLAY_PERMISSION_REQUEST = 1000;

    private Button single;
    private Button multi;
    private Button helper;
    private Button scroll;
    private Button nested;
    private Button dnd;
    private Button dragList;
    private Button swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        single = (Button) findViewById(R.id.single);
        single.setOnClickListener(this);
        multi = (Button) findViewById(R.id.multi);
        multi.setOnClickListener(this);
        helper = (Button) findViewById(R.id.helper);
        helper.setOnClickListener(this);
        scroll = (Button) findViewById(R.id.scroll);
        scroll.setOnClickListener(this);
        nested = (Button) findViewById(R.id.nested);
        nested.setOnClickListener(this);
        dnd = (Button) findViewById(R.id.dnd);
        dnd.setOnClickListener(this);
        dragList = (Button) findViewById(R.id.dragList);
        dragList.setOnClickListener(this);
        swipe = (Button) findViewById(R.id.swipe);
        swipe.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    @Override
    public void onClick(View v) {
        if (v == single) {
            Intent intent = new Intent(this, SingleActivity.class);
            startActivity(intent);
        } else if (v == multi) {
            Intent intent = new Intent(this, MultiActivity.class);
            startActivity(intent);
        } else if (v == helper) {
            Intent intent = new Intent(this, DraggerHelperActivity.class);
            startActivity(intent);
        } else if (v == scroll) {
            Intent intent = new Intent(this, ScrollActivity.class);
            startActivity(intent);
        } else if (v == nested) {
            Intent intent = new Intent(this, NestedScrollActivity.class);
            startActivity(intent);
        } else if (v == dnd) {
            Intent intent = new Intent(this, DragAndDropActivity.class);
            startActivity(intent);
        } else if (v == dragList) {
            Intent intent = new Intent(this, DragListViewActivity.class);
            startActivity(intent);
        } else if (v == swipe) {
            Intent intent = new Intent(this, SwipeActivity.class);
            startActivity(intent);
        }
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
