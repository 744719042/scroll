package com.example.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.scroll.adapter.UserListAdapter;

public class NestedScrollActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new UserListAdapter(this));
    }
}
