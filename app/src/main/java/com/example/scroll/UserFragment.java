package com.example.scroll;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.scroll.adapter.UserListAdapter;
import com.example.scroll.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    public static final String ARG_STATE = "arg_state";
    public static final String WEI = "wei";
    public static final String SHU = "shu";
    public static final String WU = "wu";

    private String state;
    private ListView listView;

    public UserFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String state) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        state = getArguments().getString(ARG_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);
        UserListAdapter adapter = null;
        if (WEI.equalsIgnoreCase(state)) {
            adapter = new UserListAdapter(getContext(), wei);
        } else if (SHU.equalsIgnoreCase(state)) {
            adapter = new UserListAdapter(getContext(), shu);
        } else {
            adapter = new UserListAdapter(getContext(), wu);
        }
        listView.setAdapter(adapter);
    }

    public static List<User> wei = new ArrayList<User>() {
        {
            add(new User(R.drawable.lake, "曹操", "宁教我负天下人，不教天下人负我"));
            add(new User(R.drawable.rain, "司马懿", "老而不死是为贼"));
            add(new User(R.drawable.sea, "司马昭", "司马昭之心路人皆知"));
        }
    };

    public static List<User> shu = new ArrayList<User>() {
        {
            add(new User(R.drawable.beach, "刘备", "唯贤唯德，能服于人"));
            add(new User(R.drawable.road, "刘禅", "此间乐，不思蜀"));
            add(new User(R.drawable.bamboo, "诸葛亮", "淡泊以明志，宁静以致远"));
            add(new User(R.drawable.blue, "关羽", "安能与老兵同列"));
            add(new User(R.drawable.flower, "张飞", "燕人张翼德在此"));
            add(new User(R.drawable.river, "赵云", "子龙一身是胆"));
            add(new User(R.drawable.rain, "马超", "前者着红袍者曹超"));
            add(new User(R.drawable.beach, "黄忠", "廉颇老矣，尚能饭否"));
            add(new User(R.drawable.lake, "魏延", "脑后有反骨，其后必反"));
        }
    };

    public static List<User> wu = new ArrayList<User>() {
        {
            add(new User(R.drawable.moon, "孙策", "江东小霸王"));
            add(new User(R.drawable.blue, "孙权", "生子当如孙仲谋"));
            add(new User(R.drawable.peach, "周瑜", "既生瑜何生亮"));
            add(new User(R.drawable.pool, "鲁肃", "忠厚长者"));
            add(new User(R.drawable.beach, "吕蒙", "士别三日当刮目相待"));
            add(new User(R.drawable.flower, "陆逊", "火烧联营七百里"));
            add(new User(R.drawable.rain, "太史慈", "一员虎将"));
            add(new User(R.drawable.river, "甘宁", "锦帆贼"));
        }
    };

}
