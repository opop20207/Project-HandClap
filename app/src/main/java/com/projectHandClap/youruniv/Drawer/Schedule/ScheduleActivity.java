package com.projectHandClap.youruniv.Drawer.Schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.ListViewAdapter;
import com.projectHandClap.youruniv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

// db에서 일정 정보 받아오기
// 마감날짜기준 정렬
// 날짜 list_schedule_date 뿌려주기
// list_schedule_date 에 list_schedule 구성해서 뿌려주기

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 mm월 dd일");
        ListView list_schedule_date = null; // 일정 날짜 정보
        ListView list_schedule = null; // 일정 정보
        TextView txv_schedule_title, txv_schedule_deadline;
        ToggleButton tbtn_schedule_done;

        // 일정 날짜 받아와서 list_schedule_date에 뿌려주기

        ArrayList<Integer> list_date = new ArrayList<Integer>();
        ArrayList<Integer> list_result_date = new ArrayList<Integer>();
        HashSet<Integer> hs = new HashSet<Integer>(list_date);

        // db에서 일정 받아오기

        // list_date.add(date)

        // 일정정보 중복 제거하고 정렬하기

        Iterator it = hs.iterator();
        while(it.hasNext()){
            list_result_date.add((Integer) it.next());
        }

        Comparator<Integer> compare = new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return integer.compareTo(t1);
            }
        };

        Collections.sort(list_result_date, compare);

        // list_schedule_date 뿌려주기

        final ArrayAdapter<Integer> adapter_date = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, list_result_date);

        list_schedule_date = findViewById(R.id.list_schedule_date);
        list_schedule_date.setAdapter(adapter_date);

        // list_schedule 구성해서 뿌려주기

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list_item = new ArrayList<String>();

        list_schedule = findViewById(R.id.list_schedule);

        ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        // list_item.add(완료유무 체크박스, title, deadline);

        // 해당 일정의 list_schedule_date에 list_item 추가하기

        list_schedule.setAdapter(adapter);

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
