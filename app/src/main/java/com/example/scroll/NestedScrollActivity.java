package com.example.scroll;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.scroll.adapter.UserListAdapter;
import com.example.scroll.model.User;
import com.example.scroll.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] states = new String[] { "魏国", "蜀国", "吴国" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        mFragments.add(UserFragment.newInstance(UserFragment.WEI));
        mFragments.add(UserFragment.newInstance(UserFragment.SHU));
        mFragments.add(UserFragment.newInstance(UserFragment.WU));

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return states[position];
            }
        });
    }
}
