package com.projectHandClap.youruniv.ViewPager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.projectHandClap.youruniv.R;

public class ViewPagerActivity extends AppCompatActivity {
    FragmentPagerAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 4;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return first.newInstance(0, "Page #1");
                case 1:
                    return second.newInstance(0, "Page #2");
                case 2:
                    return third.newInstance(0, "Page #3");
                case 3:
                    return fourth.newInstance(0, "Page #4");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}