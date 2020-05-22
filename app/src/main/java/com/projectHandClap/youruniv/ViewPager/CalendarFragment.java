package com.projectHandClap.youruniv.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;

import com.projectHandClap.youruniv.R;

public class CalendarFragment extends Fragment {

    public CalendarFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return (RelativeLayout) inflater.inflate(R.layout.activity_schedule, container,false);
    }
}
