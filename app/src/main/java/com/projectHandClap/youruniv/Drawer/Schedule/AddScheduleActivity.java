package com.projectHandClap.youruniv.Drawer.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.R;
import com.projectHandClap.youruniv.ScheduleData;
import com.projectHandClap.youruniv.ViewPager.Fragment_Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddScheduleActivity extends AppCompatActivity {
    EditText etxt_add_schedule_title, etxt_add_schedule_memo;
    ToggleButton tbtn_add_schedule_alarm, tbtn_add_schedule_check;
    TextView txv_add_schedule_date;
    TimePicker tp_add_schedule_time;
    DatePickerDialog.OnDateSetListener datePickerListener;
    String classString;
    DatabaseHelper db;

    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmm");
    long y=0, m=0, d=0;
    long selectedDate;
    long selectedTime;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String stringNow = sdfNow.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        db = new DatabaseHelper(AddScheduleActivity.this);
        initView();
    }

    public void initView(){
        etxt_add_schedule_title = (EditText) findViewById(R.id.etxt_add_schedule_title);
        etxt_add_schedule_memo = (EditText) findViewById(R.id.etxt_add_schedule_memo);

        tp_add_schedule_time = (TimePicker) findViewById(R.id.tp_add_schedule_time);
        tp_add_schedule_time.setIs24HourView(true);

        tbtn_add_schedule_alarm = (ToggleButton) findViewById(R.id.tbtn_add_schedule_alarm);
        tbtn_add_schedule_check = (ToggleButton) findViewById(R.id.tbtn_add_schedule_check);

        txv_add_schedule_date = (TextView) findViewById(R.id.txv_add_schedule_date);

        y = Long.parseLong(stringNow.substring(0,4));
        m = Long.parseLong(stringNow.substring(4,6));
        d = Long.parseLong(stringNow.substring(6,8));
        selectedDate = y * 100000000 + m * 1000000 + d * 10000;

        txv_add_schedule_date.setText(y+"."+m+"."+d);

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;
                txv_add_schedule_date.setText(y+"."+m+"."+d);
                selectedDate = y * 100000000 + m * 1000000 + d * 10000;
            }
        };
    }

    public void addToDatabaseSchedule(){
        ScheduleData scheduleData = new ScheduleData();
        scheduleData.schedule_class_string = classString;

        scheduleData.schedule_title = etxt_add_schedule_title.getText().toString();
        if(scheduleData.schedule_title.isEmpty()){
            Toast.makeText(getApplicationContext(),"you must input schedule title",Toast.LENGTH_LONG).show();
            return;
        }

        scheduleData.schedule_memo = etxt_add_schedule_memo.getText().toString();

        boolean alarmBool = tbtn_add_schedule_alarm.isActivated();
        scheduleData.schedule_alarm = (alarmBool ? "1" : "0");

        boolean checkBool = tbtn_add_schedule_check.isActivated();
        scheduleData.schedule_isDone = (checkBool ? "1" : "0");

        int time = tp_add_schedule_time.getHour()*100+tp_add_schedule_time.getMinute();
        selectedTime = (long)time;
        scheduleData.schedule_deadline = (selectedDate/10000)*10000+selectedTime;

        db.insertSchedule(scheduleData);
    }

    public void onClickAddSchedule(View v){
        Log.e("!!", v.getId()+"!");
        switch (v.getId()){
            case R.id.btn_add_schedule_submit:
                Log.e("!!","@");
                addToDatabaseSchedule();
                setResult(1);
                finish();
                break;
            case R.id.btn_add_schedule_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_scheudule_datepicker:
                DatePickerDialog dialog = new DatePickerDialog(AddScheduleActivity.this, datePickerListener, (int)y, (int)m-1, (int)d);
                dialog.show();
                break;
        }
    }
}
