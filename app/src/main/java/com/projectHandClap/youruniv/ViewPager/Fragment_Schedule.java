package com.projectHandClap.youruniv.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_schedule, container,false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        init();

        return viewGroup;
    }

    public void init(){
        Button fab = viewGroup.findViewById(R.id.btn_add_schedule);

        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddScheduleActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        layoutSchedule = (LinearLayout)viewGroup.findViewById(R.id.layout_schedule);
        setLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setLayout(){
        layoutSchedule.removeAllViews();

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        ArrayList<ScheduleData> scheduleList = db.getSchedule();

        Typeface typeface = getResources().getFont(R.font.font);

        Log.e("!!","!"+scheduleList.size());

        long nowtime = 0;

        for(ScheduleData s : scheduleList){
            long t = s.schedule_deadline%10000;
            long d = (s.schedule_deadline/10000);
            String st = String.valueOf(t);
            String sd = String.valueOf(d);
            Log.e("!!", "!+"+nowtime+"!"+s.schedule_deadline);
            if(nowtime != d){
                nowtime = d;

                TextView txvDate = new TextView(mContext);

                txvDate.setText(String.format(sd));
                txvDate.setLayoutParams(layoutParams);
                txvDate.setTextSize(15);
                txvDate.setTypeface(typeface);
                txvDate.getTypeface();
                txvDate.setPadding(30,50,15, 0);
                layoutSchedule.addView(txvDate);
            }

            LinearLayout ll = new LinearLayout(mContext);
            ll.setLayoutParams(layoutParams);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView txvTitle = new TextView(mContext);
            txvTitle.setLayoutParams(layoutParams);
            txvTitle.setText(s.schedule_title);
            txvTitle.setTypeface(typeface);
            txvTitle.setPadding(100, 15,15,0);
            txvTitle.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            TextView txvTime = new TextView(mContext);
            txvTime.setLayoutParams(layoutParams);
            txvTime.setText(String.format("~%02d:%02d", t/100, t%100));
            txvTime.setTypeface(typeface);
            txvTime.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2));

            Button btnDelete = new Button(mContext);
            btnDelete.setText("삭제");
            btnDelete.setTypeface(typeface);
            btnDelete.setBackgroundColor(Color.TRANSPARENT);
            btnDelete.setPadding(0, 15, 10, 0);
            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2));
            final ScheduleData fs = s;
            btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteSchedule(fs);
                    setLayout();
                }
            });

            ll.addView(txvTitle);
            ll.addView(txvTime);
            ll.addView(btnDelete);
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
