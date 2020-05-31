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

import com.projectHandClap.youruniv.R;

public class ViewPagerActivity extends AppCompatActivity {
    FragmentPagerAdapter viewPageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        Button btn_viewpager_gallery = (Button) findViewById(R.id.btn_Viewpager_Gallery);
        Button btn_viewpager_recorder = (Button) findViewById(R.id.btn_Viewpager_Recorder);
        Button btn_viewpager_memo = (Button) findViewById(R.id.btn_Viewpager_Memo);
        Button btn_viewpager_schedule = (Button) findViewById(R.id.btn_Viewpager_Schedule);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        viewPager.setCurrentItem(position, true);

        btn_viewpager_gallery.setTag(0);
        btn_viewpager_recorder.setTag(1);
        btn_viewpager_memo.setTag(2);
        btn_viewpager_schedule.setTag(3);

        btn_viewpager_gallery.setOnClickListener(movePageListener);
        btn_viewpager_recorder.setOnClickListener(movePageListener);
        btn_viewpager_memo.setOnClickListener(movePageListener);
        btn_viewpager_schedule.setOnClickListener(movePageListener);
    }

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = (int)view.getTag();
            viewPager.setCurrentItem(tag, true);
        }
    };

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 4;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Gallery();
                case 1:
                    return new Fragment_Recorder();
                case 2:
                    return new Fragment_Memo();
                case 3:
                    return new Fragment_Schedule();
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