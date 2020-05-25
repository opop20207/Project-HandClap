package com.projectHandClap.youruniv.Drawer.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.R;

/*
DB
날짜  - 날짜별 일정 - 일정 세부사항
date
title isDone memo deadline
 */

// 날짜 list_schedule_date 뿌려주기
// list_schedule_date 에 list_schedule 구성해서 뿌려주기

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        FloatingActionButton fab = findViewById(R.id.add_calendar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleActivity.this, AddScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}
