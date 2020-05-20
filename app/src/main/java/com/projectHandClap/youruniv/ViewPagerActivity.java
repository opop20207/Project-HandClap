package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewParentCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        Button ViewPager_Gallery = (Button)findViewById(R.id.Viewpager_Gallery);
        Button ViewPager_Recorder = (Button)findViewById(R.id.Viewpager_Recorder);
        Button ViewPager_Memo = (Button)findViewById(R.id.Viewpager_Memo);
        Button ViewPager_Calendar = (Button)findViewById(R.id.Viewpager_Calendar);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        ViewPager_Gallery.setOnClickListener(movePageListener);
        ViewPager_Gallery.setTag(0);

        ViewPager_Recorder.setOnClickListener(movePageListener);
        ViewPager_Recorder.setTag(0);

        ViewPager_Memo.setOnClickListener(movePageListener);
        ViewPager_Memo.setTag(0);

        ViewPager_Calendar.setOnClickListener(movePageListener);
        ViewPager_Calendar.setTag(0);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            int tag = (int)view.getTag();
            viewPager.setCurrentItem(tag);
        }
    };

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new GalleryFragment();
                case 1:
                    return new RecorderFragment();
                case 2:
                    return new MemoFragment();
                case 3:
                    return new CalendarFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    }
}
