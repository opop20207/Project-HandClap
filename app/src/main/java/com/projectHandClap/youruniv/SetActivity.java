package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        init();
    }

    public void init(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<TimetableData> t = db.getTimetable();
        final List<String> listItems = new ArrayList<>();
        for(TimetableData t1 : t){
            listItems.add(t1.timetable_title);
        }

        TextView txv_setting_main_timetable_id = (TextView)findViewById(R.id.txv_setting_main_timetable_id);
        txv_setting_main_timetable_id.setText("1");
    }

    public void onClickSetting(View v){
        switch(v.getId()){
            case R.id.btn_setting_submit:
                break;
            case R.id.btn_setting_main_timetable_id:
                break;
        }
    }
}
