package com.example.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.scroll.adapter.UserListAdapter;

public class DragListViewActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list_view);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new UserListAdapter(this));
    }
}
