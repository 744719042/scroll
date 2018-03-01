package com.example.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.scroll.adapter.SwipeUserListAdapter;
import com.example.scroll.adapter.UserListAdapter;

public class SwipeActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SwipeUserListAdapter(this));
    }
}
