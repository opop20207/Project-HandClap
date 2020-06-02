package com.projectHandClap.youruniv.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.projectHandClap.youruniv.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    FragmentPagerAdapter viewPageAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        init();
    }

    public void init(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        viewPager.setCurrentItem(position, true);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    public void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_gallery);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_record);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_memo);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_schedule);
    }

    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Gallery(), "Gallery");
        adapter.addFrag(new Fragment_Recorder(), "Recorder");
        adapter.addFrag(new Fragment_Memo(), "Memo");
        adapter.addFrag(new Fragment_Schedule(), "Schedule");
        viewPager.setAdapter(adapter);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 4;

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}