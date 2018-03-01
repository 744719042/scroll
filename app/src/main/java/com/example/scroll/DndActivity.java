package com.example.scroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.scroll.adapter.DragableUserListAdapter;

public class DndActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dnd);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new DragableUserListAdapter(this));
    }
}
