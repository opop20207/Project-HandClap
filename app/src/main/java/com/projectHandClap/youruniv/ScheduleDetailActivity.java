package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.projectHandClap.youruniv.Drawer.Schedule.AddScheduleActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleDetailActivity extends AppCompatActivity {
    EditText etxt_add_schedule_title, etxt_add_schedule_memo;
    TextView txv_add_schedule_date;
    TimePicker tp_add_schedule_time;
    DatePickerDialog.OnDateSetListener datePickerListener;

    DatabaseHelper db;
    ScheduleData sData;
    int sid;

    long y=0, m=0, d=0;
    long selectedDate;
    long selectedTime;
    String stringNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        db = new DatabaseHelper(ScheduleDetailActivity.this);
        initView();
    }

    public void initView(){
        sid = getIntent().getIntExtra("scheduleId", 1);
        sData = db.getScheduleById(sid);
        stringNow = String.valueOf(sData.schedule_deadline);

        etxt_add_schedule_title = (EditText) findViewById(R.id.etxt_add_schedule_title);
        etxt_add_schedule_memo = (EditText) findViewById(R.id.etxt_add_schedule_memo);

        etxt_add_schedule_title.setText(sData.schedule_title);
        etxt_add_schedule_memo.setText(sData.schedule_memo);

        int hou = Integer.parseInt(stringNow.substring(8,10));
        int min = Integer.parseInt(stringNow.substring(10,12));
        tp_add_schedule_time = (TimePicker) findViewById(R.id.tp_add_schedule_time);
        tp_add_schedule_time.setIs24HourView(true);
        tp_add_schedule_time.setHour(hou);
        tp_add_schedule_time.setMinute(min);

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
        scheduleData.schedule_id = sData.schedule_id;
        scheduleData.schedule_class_string = sData.schedule_class_string;

        scheduleData.schedule_title = etxt_add_schedule_title.getText().toString();

        scheduleData.schedule_memo = etxt_add_schedule_memo.getText().toString();

        scheduleData.schedule_alarm = "1";
        scheduleData.schedule_isDone = "1";

        int time = tp_add_schedule_time.getHour()*100+tp_add_schedule_time.getMinute();
        selectedTime = (long)time;
        scheduleData.schedule_deadline = (selectedDate/10000)*10000+selectedTime;

        db.updateSchedule(scheduleData);
    }

    public void onClickAddSchedule(View v){
        Log.e("!!", v.getId()+"!");
        switch (v.getId()){
            case R.id.btn_add_schedule_submit:
                if(etxt_add_schedule_title.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"you must input schedule title",Toast.LENGTH_LONG).show();
                    return;
                }
                addToDatabaseSchedule();
                setResult(1);
                finish();
                break;
            case R.id.btn_add_schedule_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_scheudule_datepicker:
                DatePickerDialog dialog = new DatePickerDialog(ScheduleDetailActivity.this, datePickerListener, (int)y, (int)m-1, (int)d);
                dialog.show();
                break;
        }
    }
}
