package com.projectHandClap.youruniv.Drawer.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 mm월 dd일");
        ListView list_schedule_date = null;
        ListView list_schedule = null;
        TextView txv_schedule_title, txv_schedule_deadline;
        CheckBox chkbox_schedule_done;

        ArrayList<String> list_item = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.activity_list_item, list_item);

        // list_item.add(완료유무 체크박스, title, date, deadline);

        Collections.sort(list_item);

        class ListItem implements Comparable{
            private int done;
            private String title ="";
            private String time = "";
            private String date = "";

            public ListItem(int done, String title, String time, String date){
                this.done = done;
                this.title = title;
                this.date = date;
                this.time = time;
            }

            @Override
            public int compareTo(Object o) {
                return this.title.compareTo(this.title);
            }

            public void setDone(int done)   {this.done = done;}
            public int getDone() {return this.done;}
            public void setTitle(String title)  {this.title = title;}
            public String getTitle()    {return this.title;}
            public void setTime(String time) {this.time = time;}
            public  String getTime()    {return this.time;}
            public void setDate(String date)    {this.date = date;}
            public String getDate() {return this.date;}

            public String toString(){
                StringBuilder sb = new StringBuilder();
                sb.append(this.done).append(" ");
                sb.append(this.title);
                sb.append("~").append(this.time);
                return sb.toString();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        FloatingActionButton fab = findViewById(R.id.add_calendar);

        list_schedule_date = findViewById(R.id.list_schedule_date);
        list_schedule = findViewById(R.id.list_schedule);
        txv_schedule_title = findViewById(R.id.txv_schedule_title);
        txv_schedule_deadline = findViewById(R.id.txv_schedule_deadline);
        chkbox_schedule_done = findViewById(R.id.chkbox_schedule_done);

        // db에서 일정 정보 받아오기
        // 마감날짜기준 정렬

        // 날짜 list_schedule_date 뿌려주기
        // list_schedule_date 에 list_schedule 구성해서 뿌려주기
    }
}
