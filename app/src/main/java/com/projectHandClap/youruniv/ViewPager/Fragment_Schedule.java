package com.projectHandClap.youruniv.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.Drawer.Schedule.AddScheduleActivity;
import com.projectHandClap.youruniv.Drawer.Schedule.ScheduleActivity;
import com.projectHandClap.youruniv.R;
import com.projectHandClap.youruniv.ScheduleData;

import java.util.ArrayList;


public class Fragment_Schedule extends Fragment {
    ViewPagerActivity viewPagerActivity;
    ViewGroup viewGroup;
    LinearLayout layoutSchedule;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_schedule, container,false);

        db = new DatabaseHelper(viewGroup.getContext());
        init();

        return viewGroup;
    }

    public void init(){
        FloatingActionButton fab = viewGroup.findViewById(R.id.btn_add_schedule);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(viewGroup.getContext(), AddScheduleActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        layoutSchedule = (LinearLayout)viewGroup.findViewById(R.id.layout_schedule);
        setLayout();
    }

    public void setLayout(){
        layoutSchedule.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ArrayList<ScheduleData> scheduleList = db.getSchedule();
        Log.e("!!","!"+scheduleList.size());
        long nowtime = 0;
        Context context = viewGroup.getContext();
        for(ScheduleData s : scheduleList){
            long d = (s.schedule_deadline/10000)*10000;
            Log.e("!!", "!+"+nowtime+"!"+s.schedule_deadline);
            if(nowtime!=d){
                nowtime = d;
                TextView txvDate = new TextView(context);
                txvDate.setText(String.valueOf(d));
                txvDate.setLayoutParams(layoutParams);
                Log.e("!!","@@@@@@@@@@");
                layoutSchedule.addView(txvDate);
            }
            LinearLayout ll = new LinearLayout(context);
            ll.setLayoutParams(layoutParams);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView txvTime = new TextView(context);
            txvTime.setLayoutParams(layoutParams);
            txvTime.setText("일정 기간 : " + String.valueOf(s.schedule_deadline));
            TextView txvTitle = new TextView(context);
            txvTitle.setLayoutParams(layoutParams);
            txvTitle.setText("일정 제목 : "+s.schedule_title);

            layoutSchedule.addView(ll);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==1){
            setLayout();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        viewPagerActivity = (ViewPagerActivity) getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        viewPagerActivity = null;
    }

    public Fragment_Schedule(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
