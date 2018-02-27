package com.example.scroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button single;
    private Button multi;
    private Button helper;
    private Button scroll;
    private Button nested;

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
        }
    }
}
