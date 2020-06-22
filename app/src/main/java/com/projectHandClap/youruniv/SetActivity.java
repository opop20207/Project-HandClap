package com.projectHandClap.youruniv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity {
    TimePicker tp_setting_stime, tp_setting_etime;
    CheckBox chkbox_setting_day_1,  chkbox_setting_day_2, chkbox_setting_day_3;
    CheckBox chkbox_setting_day_4,  chkbox_setting_day_5, chkbox_setting_day_6, chkbox_setting_day_7;
    TextView txv_setting_main_timetable_id;

    boolean [] chk = {false, false, false ,false ,false,false ,false, false};
    int newTimetableId;
    SettingData retSetting;
    NumberPicker minutePicker;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        db = new DatabaseHelper(getApplicationContext());
        init();
    }

    private static final int INTERVAL = 15;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    public void setTimePickerInterval(TimePicker timePicker){
        int numValues = 60 / INTERVAL;
        String[] displayedValues = new String[numValues];
        for (int i = 0; i < numValues; i++) {
            displayedValues[i] = FORMATTER.format(i * INTERVAL);
        }
        View minute = timePicker.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        if ((minute != null) && (minute instanceof NumberPicker)) {
            minutePicker = (NumberPicker) minute;
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(numValues - 1);
            minutePicker.setDisplayedValues(displayedValues);
        }
    }

    public void init(){
        retSetting = db.getSetting();
        newTimetableId = retSetting.setting_main_timetable_id;

        String temp = retSetting.setting_day;
        for(int i=0;i<temp.length();i++){
            int k = Integer.parseInt(String.valueOf(temp.charAt(i)));
            chk[k] = true;
        }
        Log.e("!!", "!@");
        tp_setting_stime = (TimePicker) findViewById(R.id.tp_setting_stime);
        tp_setting_etime = (TimePicker) findViewById(R.id.tp_setting_etime);

        Log.e("!!", "!@2");
        int sh, sm, eh, em;
        sh = Integer.parseInt(retSetting.setting_stime)/100;
        sm = Integer.parseInt(retSetting.setting_stime)%100;
        eh = Integer.parseInt(retSetting.setting_etime)/100;
        em = Integer.parseInt(retSetting.setting_etime)%100;

        setTimePickerInterval(tp_setting_stime);
        tp_setting_stime.setIs24HourView(true);
        tp_setting_stime.setHour(sh);
        tp_setting_stime.setMinute(sm/15);
        tp_setting_stime.setClickable(false);

        setTimePickerInterval(tp_setting_etime);
        tp_setting_etime.setIs24HourView(true);
        tp_setting_etime.setHour(eh);
        tp_setting_etime.setMinute(em/15);
        tp_setting_etime.setClickable(false);

        txv_setting_main_timetable_id = (TextView)findViewById(R.id.txv_setting_main_timetable_id);
        chkbox_setting_day_1 = (CheckBox) findViewById(R.id.chkbox_setting_day_1);
        chkbox_setting_day_1.setChecked(chk[1]);
        chkbox_setting_day_2 = (CheckBox) findViewById(R.id.chkbox_setting_day_2);
        chkbox_setting_day_2.setChecked(chk[2]);
        chkbox_setting_day_3 = (CheckBox) findViewById(R.id.chkbox_setting_day_3);
        chkbox_setting_day_3.setChecked(chk[3]);
        chkbox_setting_day_4 = (CheckBox) findViewById(R.id.chkbox_setting_day_4);
        chkbox_setting_day_4.setChecked(chk[4]);
        chkbox_setting_day_5 = (CheckBox) findViewById(R.id.chkbox_setting_day_5);
        chkbox_setting_day_5.setChecked(chk[5]);
        chkbox_setting_day_6 = (CheckBox) findViewById(R.id.chkbox_setting_day_6);
        chkbox_setting_day_6.setChecked(chk[6]);
        chkbox_setting_day_7 = (CheckBox) findViewById(R.id.chkbox_setting_day_7);
        chkbox_setting_day_7.setChecked(chk[7]);


        TimetableData timetableData = db.getTimetableOne((int)retSetting.setting_main_timetable_id);
        txv_setting_main_timetable_id.setText(timetableData.timetable_title);

        CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch(compoundButton.getId()){
                    case R.id.chkbox_setting_day_1:
                        chk[1] = b;
                        break;
                    case R.id.chkbox_setting_day_2:
                        chk[2] = b;
                        break;
                    case R.id.chkbox_setting_day_3:
                        chk[3] = b;
                        break;
                    case R.id.chkbox_setting_day_4:
                        chk[4] = b;
                        break;
                    case R.id.chkbox_setting_day_5:
                        chk[5] = b;
                        break;
                    case R.id.chkbox_setting_day_6:
                        chk[6] = b;
                        break;
                    case R.id.chkbox_setting_day_7:
                        chk[7] = b;
                        break;
                }
            }
        };
        chkbox_setting_day_1.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_2.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_3.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_4.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_5.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_6.setOnCheckedChangeListener(checkedChangeListener);
        chkbox_setting_day_7.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void submit(){
        retSetting.setting_stime = String.valueOf(tp_setting_stime.getHour()*100+tp_setting_stime.getMinute()*15);
        retSetting.setting_etime = String.valueOf(tp_setting_etime.getHour()*100+tp_setting_etime.getMinute()*15);
        String retDay="";
        for(int i=1;i<=7;i++){
            if(chk[i]){
                retDay+=String.valueOf(i);
            }
        }
        retSetting.setting_day = retDay;
        db.updateSetting(retSetting);
    }

    public void showDialog(){
        final List<String> listItems = new ArrayList<>();
        final List<Integer> listItems_id = new ArrayList<>();
        final List<Integer> selectedItems = new ArrayList<>();

        int checkedItem = 0;

        ArrayList<TimetableData> t = db.getTimetable();
        for(int i=0;i<t.size();i++){
            TimetableData t1 = t.get(i);
            if(t1.timetable_id == retSetting.setting_main_timetable_id){
                Log.e("!!!!!",t1.timetable_title+"!!!"+i);
                checkedItem = i;
            }
            listItems.add(t1.timetable_title);
            listItems_id.add((int)t1.timetable_id);
        }
        final CharSequence[] items = listItems.toArray(new String[listItems.size()]);
        AlertDialog.Builder dlg = new AlertDialog.Builder(SetActivity.this);
        dlg.setTitle("메인 시간표 설정");
        Log.e("ckditem", checkedItem+"!"+retSetting.setting_main_timetable_id);
        dlg.setSingleChoiceItems(items,checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedItems.clear();
                selectedItems.add(i);
            }
        });
        dlg.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(selectedItems.size()==0){
                    Toast.makeText(getApplicationContext(),"No selection", Toast.LENGTH_LONG).show();
                    return;
                }
                int idx = (int)selectedItems.get(0);
                retSetting.setting_main_timetable_id = listItems_id.get(idx);
                newTimetableId = listItems_id.get(idx);
                txv_setting_main_timetable_id.setText(listItems.get(idx));
            }
        });
        dlg.create().show();
    }

    public void onClickSetting(View v){
        switch(v.getId()){
            case R.id.btn_setting_submit:
                submit();
                Intent intent = new Intent();
                intent.putExtra("newTimetableId", newTimetableId);
                setResult(2, intent);
                finish();
                break;
            case R.id.btn_setting_main_timetable_id:
                showDialog();
                break;
        }
    }
}
