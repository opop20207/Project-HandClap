package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class AddScheduleActivity extends AppCompatActivity {
    private EditText etxt_add_schedule_name, etxt_add_schedule_content;
    private ToggleButton tbtn_add_schedule_alarm, tbtn_add_schedule_check;
    private Button btn_add_schedule_cancel, btn_add_schdule_submit;
    private DatePicker dp_add_schedule_date;
    private TimePicker tp_add_schedule_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

    }

    public void initView(){
        EditText etxt_add_class_title = (EditText) findViewById(R.id.etxt_add_schedule_name);
        EditText etxt_add_schedule_content = (EditText) findViewById(R.id.etxt_add_schedule_content);

        TimePicker tp_add_schedule_time = (TimePicker) findViewById(R.id.tp_add_schedule_time);
        DatePicker dp_add_schedule_date = (DatePicker) findViewById(R.id.dp_add_schedule_date);

        ToggleButton tbtn_add_schedule_alarm = (ToggleButton) findViewById(R.id.tbtn_add_schedule_alarm);
        ToggleButton tbtn_add_achedule_alarm = (ToggleButton) findViewById(R.id.tbtn_add_schedule_check);
    }

    public void onClickAddSchedule(View v){
        switch (v.getId()){
            case R.id.btn_add_schedule_submit:
                break;
            case R.id.btn_add_schedule_cancel:
                break;
        }
    }
}
