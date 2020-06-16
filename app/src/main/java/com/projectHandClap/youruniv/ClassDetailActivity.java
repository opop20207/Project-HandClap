package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassDetailActivity extends AppCompatActivity {
    EditText etxt_add_class_title, etxt_add_class_place, etxt_add_class_professor, etxt_add_class_memo;
    RadioGroup rgroup_add_class_color;
    String [] tableDay = {"S","M","T","W","T","F","S","S"};

    ArrayList<TimeData> addClassArray;

    int cid;
    String cstr;
    DatabaseHelper db;
    ClassData cd;
    ArrayList<ClassData> clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        db = new DatabaseHelper(this);
        init();
        setLayout();
    }

    public void init(){
        cid = getIntent().getIntExtra("classDataId", 1);

        etxt_add_class_title = (EditText) findViewById(R.id.etxt_add_class_title);
        etxt_add_class_place = (EditText) findViewById(R.id.etxt_add_class_place);
        etxt_add_class_professor = (EditText)findViewById(R.id.etxt_add_class_professor);
        etxt_add_class_memo = (EditText) findViewById(R.id.etxt_add_class_memo);

        rgroup_add_class_color = (RadioGroup) findViewById(R.id.rgroup_add_class_color);
    }

    public void setLayout(){
        addClassArray = new ArrayList<>();
        cd = db.getClassDataOneById(cid);
        Log.e("!!!", cid+"!!");
        cstr = cd.class_string;
        clist = db.getClassDataByClassString(cstr);
        Log.e("!!", clist.size()+"!");
        for(ClassData classData : clist){
            final TimeData timeData = new TimeData();
            timeData.day = classData.class_day;
            int dayNum = Integer.parseInt(timeData.day);
            timeData.stime = Integer.parseInt(classData.class_stime);
            timeData.etime = Integer.parseInt(classData.class_etime);
            addClassArray.add(timeData);

            final LinearLayout ll_add_class_time = (LinearLayout)findViewById(R.id.ll_add_class_time);
            ll_add_class_time.setOrientation(LinearLayout.VERTICAL);

            final LinearLayout ll = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            ll.setGravity(Gravity.CENTER);
            TextView tv = new TextView(getApplicationContext());
            tv.setText(tableDay[dayNum]+" - "+timeData.stime+" ~ "+timeData.etime);
            tv.setLayoutParams(layoutParams);
            tv.setTypeface(getResources().getFont(R.font.font));
            tv.setGravity(Gravity.CENTER);

            ll.addView(tv);

            ll_add_class_time.addView(ll);
        }
        int colorId = Integer.parseInt(cd.class_color);
        RadioButton rbtn_c = null;
        switch(colorId){
            case 1:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_1);
                break;
            case 2:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_2);
                break;
            case 3:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_3);
                break;
            case 4:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_4);
                break;
            case 5:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_5);
                break;
            case 6:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_6);
                break;
            case 7:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_7);
                break;
            case 8:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_8);
                break;
            case 9:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_9);
                break;
            case 10:
                rbtn_c = findViewById(R.id.rbtn_add_class_color_10);
                break;
        }
        rbtn_c.setChecked(true);
        etxt_add_class_title.setText(cd.class_title);
        etxt_add_class_place.setText(cd.class_place);
        etxt_add_class_professor.setText(cd.class_professor);
        etxt_add_class_memo.setText(cd.class_memo);
    }

    public void onClickAddClass(View v){
        switch (v.getId()){
            case R.id.btn_add_class_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_class_submit:
                addToDatabase_Class();
                break;
        }
    }

    public void addToDatabase_Class(){
        String toStr = null;
        if(addClassArray.size()==0){
            //no class error
            Toast.makeText(getApplicationContext(), "추가할 시간표 없음", Toast.LENGTH_LONG).show();
            return;
        }
        int cnt = 0;
        for(TimeData temp : addClassArray){
            ClassData classData = new ClassData();

            classData.class_id = clist.get(cnt).class_id;
            classData.class_string = clist.get(cnt).class_string;
            cnt++;

            classData.class_timetable_id = cd.class_timetable_id;

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

            classData.class_alarm = "1";

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
            Log.e("classData.class_memo", classData.class_memo);
            db.updateClassData(classData);
            Log.e("!!","!!@#@");
        }

        setResult(5);
        finish();
    }
}
