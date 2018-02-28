package com.example.scroll.adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scroll.R;
import com.example.scroll.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2018/2/22.
 */

public class DragableUserListAdapter extends BaseAdapter {
    private static final String TAG = "UserListAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<User> data;

    public DragableUserListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = users;
    }

    public DragableUserListAdapter(Context context, List<User> users) {
        this(context);
        this.data = users;
    }

    public static List<User> users = new ArrayList<User>() {
        {
            add(new User(R.drawable.beach, "刘备", "唯贤唯德，能服于人"));
            add(new User(R.drawable.bamboo, "诸葛亮", "淡泊以明志，宁静以致远"));
            add(new User(R.drawable.road, "关羽", "安能与老兵同列"));
            add(new User(R.drawable.flower, "赵云", "子龙一身是胆"));
            add(new User(R.drawable.lake, "曹操", "宁教我负天下人，不教天下人负我"));
            add(new User(R.drawable.rain, "司马懿", "老而不死是为贼"));
            add(new User(R.drawable.sea, "司马昭", "司马昭之心路人皆知"));
            add(new User(R.drawable.moon, "孙权", "生子当如孙仲谋"));
            add(new User(R.drawable.peach, "周瑜", "既生瑜何生亮"));
            add(new User(R.drawable.pool, "吕蒙", "士别三日当刮目相待"));
        }
    };

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindView(getItem(position));

        final View dragView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(String.valueOf(position));
                ClipData clipData = new ClipData("", new String[] {
                        ClipDescription.MIMETYPE_TEXT_PLAIN
                }, item);
                dragView.startDrag(clipData, new View.DragShadowBuilder(dragView), position, 0);
                return true;
            }
        });

        convertView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(TAG, "target: action = ACTION_DRAG_STARTED, clipData = " + event.getClipData());
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) &&
                                Integer.parseInt(String.valueOf(event.getLocalState())) != position) {
                            return true;
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(TAG, "target: action = ACTION_DRAG_ENTERED, clipData = " + event.getClipData());
                        dragView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(TAG, "target: action = ACTION_DRAG_LOCATION, clipData = " + event.getClipData());
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(TAG, "target: action = ACTION_DRAG_EXITED, clipData = " + event.getClipData());
                        dragView.setBackgroundColor(Color.WHITE);
                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.d(TAG, "target: action = ACTION_DROP, clipData = " + event.getClipData());
                        final int srcPosition = Integer.parseInt((String) event.getClipData().getItemAt(0).getText());
                        if (srcPosition == position) {
                            return true;
                        }
                        Collections.swap(users, srcPosition, position);
                        notifyDataSetChanged();
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(TAG, "target: action = ACTION_DRAG_ENDED, clipData = " + event.getClipData());
                        dragView.setBackgroundColor(Color.WHITE);
                        return true;
                }
                return false;
            }
        });

        return convertView;
    }

    public class ViewHolder {
        ImageView portrait;
        TextView name;
        TextView desc;

        public ViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            portrait = (ImageView) itemView.findViewById(R.id.portrait);
        }

        public void bindView(final User user) {
            name.setText(user.getName());
            desc.setText(user.getDesc());
            portrait.setImageResource(user.getPortrait());
        }
    }
}
