package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends AppCompatActivity {
    EditText etxt_add_class_title, etxt_add_class_place, etxt_add_class_professor, etxt_add_class_memo;
    TimePicker tp_add_class_stime, tp_add_class_etime;
    RadioGroup rgroup_add_class_color, rgroup_add_class_day;
    ToggleButton tbtn_add_class_alarm;

    String [] tableDay = {"S","M","T","W","T","F","S","S"};
    DatabaseHelper db;

    List<String> displayedMinute;
    private int TIME_PICKER_INTERVAL = 15;
    NumberPicker minutePicker;

    public class TimeData{
        int stime;
        int etime;
        String day;
    }
    ArrayList<TimeData> addClassArray;

    int timetableId;
    int timeInterval = 100;
    SettingData settingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        db = new DatabaseHelper(getApplicationContext());
        init();
    }

    public void setTimePickerInterval(TimePicker timePicker){
        try{
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field field = classForid.getField("minute");
            minutePicker = (NumberPicker) timePicker.findViewById(field.getInt(null));

            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(3);
            minutePicker.setDisplayedValues(displayedMinute.toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        initView();

        addClassArray = new ArrayList<>();

        timetableId = getIntent().getIntExtra("timetableId", 2);
        Log.e("timetableId", "!"+timetableId);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        settingData = db.getSetting();

        displayedMinute = new ArrayList<>();
        for(int k=0;k<3;k++){
            for(int i=0;i<60;i+=TIME_PICKER_INTERVAL){
                displayedMinute.add(String.format("%02d", i));
            }
        }
        setTimePickerInterval(tp_add_class_stime);
        tp_add_class_stime.setIs24HourView(true);

        setTimePickerInterval(tp_add_class_etime);
        tp_add_class_etime.setIs24HourView(true);
    }

    public void initView(){
        etxt_add_class_title = (EditText) findViewById(R.id.etxt_add_class_title);
        etxt_add_class_place = (EditText) findViewById(R.id.etxt_add_class_place);
        etxt_add_class_professor = (EditText)findViewById(R.id.etxt_add_class_professor);
        etxt_add_class_memo = (EditText) findViewById(R.id.etxt_add_class_memo);

        tp_add_class_stime = (TimePicker) findViewById(R.id.tp_add_class_stime);
        tp_add_class_etime = (TimePicker) findViewById(R.id.tp_add_class_etime);

        rgroup_add_class_color = (RadioGroup) findViewById(R.id.rgroup_add_class_color);
        rgroup_add_class_day = (RadioGroup) findViewById(R.id.rgorup_add_class_day);

        tbtn_add_class_alarm = (ToggleButton) findViewById(R.id.tbtn_add_class_alarm);
    }

    public void onClickAddClass(View v){
        switch (v.getId()){
            case R.id.btn_add_class_submit:
                addToDatabase_Class();
                break;
            case R.id.btn_add_class_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_class_time:
                addDatabaseList();
                break;
        }
    }

    public void addDatabaseList(){
        final TimeData timeData = new TimeData();
        int dayId = rgroup_add_class_day.getCheckedRadioButtonId();
        int dayNum = 0;
        switch(dayId){
            case R.id.rbtn_add_class_day_1:
                dayNum = 1;
                break;
            case R.id.rbtn_add_class_day_2:
                dayNum = 2;
                break;
            case R.id.rbtn_add_class_day_3:
                dayNum = 3;
                break;
            case R.id.rbtn_add_class_day_4:
                dayNum = 4;
                break;
            case R.id.rbtn_add_class_day_5:
                dayNum = 5;
                break;
            case R.id.rbtn_add_class_day_6:
                dayNum = 6;
                break;
            case R.id.rbtn_add_class_day_7:
                dayNum = 7;
                break;
        }
        if(dayNum==0){
            //day error
            Toast.makeText(getApplicationContext(),"no day info",Toast.LENGTH_LONG).show();
            return;
        }
        timeData.day = String.valueOf(dayNum);

        timeData.stime = tp_add_class_stime.getHour()*100+tp_add_class_stime.getMinute();
        timeData.etime = tp_add_class_etime.getHour()*100+tp_add_class_etime.getMinute();
        if(timeData.stime>=timeData.etime){
            //time setting error
            Toast.makeText(getApplicationContext(),"start time must be faster than end time",Toast.LENGTH_LONG).show();
            return;
        }
        if(timeData.stime<Integer.parseInt(settingData.setting_stime) ||
           timeData.etime>Integer.parseInt(settingData.setting_etime)){
            //setting error
            Toast.makeText(getApplicationContext(), "out of setting time", Toast.LENGTH_LONG).show();
            return;
        }

        for(TimeData t : addClassArray){
            if(Integer.parseInt(t.day) == Integer.parseInt(timeData.day)){
                if(t.stime>=timeData.etime || t.etime<=timeData.stime){
                    //time error
                    Toast.makeText(getApplicationContext(), "시간 중복", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

        final LinearLayout ll_add_class_time = (LinearLayout)findViewById(R.id.ll_add_class_time);
        ll_add_class_time.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout ll = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        TextView tv = new TextView(getApplicationContext());
        tv.setText(tableDay[dayNum]+" - "+timeData.stime+" ~ "+timeData.etime);
        tv.setLayoutParams(layoutParams);

        Button btn = new Button(getApplicationContext());
        btn.setText("삭제");
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_add_class_time.removeView(ll);
                addClassArray.remove(timeData);
            }
        });
        btn.setLayoutParams(layoutParams);

        ll.addView(tv);
        ll.addView(btn);

        ll_add_class_time.addView(ll);

        addClassArray.add(timeData);
    }

    public void addToDatabase_Class(){
        String toStr = null;
        if(addClassArray.size()==0){
            //no class error
            Toast.makeText(getApplicationContext(), "추가할 시간표 없음", Toast.LENGTH_LONG).show();
            return;
        }
        for(TimeData temp : addClassArray){
            ClassData classData = new ClassData();
            classData.class_timetable_id = timetableId;

            classData.class_title = etxt_add_class_title.getText().toString();
            if(classData.class_title.isEmpty()){
                Toast.makeText(getApplicationContext(),"you must input class title",Toast.LENGTH_LONG).show();
                return;
            }

            classData.class_place = etxt_add_class_place.getText().toString();
            if(classData.class_place.isEmpty()){
                Toast.makeText(getApplicationContext(),"you must input classroom",Toast.LENGTH_LONG).show();
                return;
            }
            classData.class_professor = etxt_add_class_professor.getText().toString();

            classData.class_day = temp.day;
            classData.class_stime = String.valueOf(temp.stime);
            classData.class_etime = String.valueOf(temp.etime);
            if(toStr == null){
                toStr = classData.class_timetable_id+classData.class_stime+classData.class_etime+classData.class_day;
            }
            classData.class_string = toStr;

            boolean alarmBool = tbtn_add_class_alarm.isActivated();
            classData.class_alarm = (alarmBool ? "1" : "0");

            int colorId = rgroup_add_class_color.getCheckedRadioButtonId();
            int colorNum = 0;
            switch(colorId){
                case R.id.rbtn_add_class_color_1:
                    colorNum = 1;
                    break;
                case R.id.rbtn_add_class_color_2:
                    colorNum = 2;
                    break;
                case R.id.rbtn_add_class_color_3:
                    colorNum = 3;
                    break;
                case R.id.rbtn_add_class_color_4:
                    colorNum = 4;
                    break;
                case R.id.rbtn_add_class_color_5:
                    colorNum = 5;
                    break;
                case R.id.rbtn_add_class_color_6:
                    colorNum = 6;
                    break;
                case R.id.rbtn_add_class_color_7:
                    colorNum = 7;
                    break;
                case R.id.rbtn_add_class_color_8:
                    colorNum = 8;
                    break;
                case R.id.rbtn_add_class_color_9:
                    colorNum = 9;
                    break;
                case R.id.rbtn_add_class_color_10:
                    colorNum = 10;
                    break;
            }
            classData.class_color = String.valueOf(colorNum);

            classData.class_memo = etxt_add_class_memo.getText().toString();

            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.insertClassData(classData);

            setResult(1);
            finish();
        }
    }
}
