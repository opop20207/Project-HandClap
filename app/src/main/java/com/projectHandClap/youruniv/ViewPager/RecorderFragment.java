package com.projectHandClap.youruniv.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.projectHandClap.youruniv.R;

public class RecorderFragment extends Fragment {

    public RecorderFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return (ConstraintLayout)inflater.inflate(R.layout.activity_recorder, container,false);
    }
}
