package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class AddTimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
    }

    public void onClickAddTimetable(View v){
        if(v.getId()==R.id.btn_add_timetable_submit){
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());

            EditText editText = (EditText)findViewById(R.id.etxt_add_timetable_title);
            String timetableTitle = editText.getText().toString();
            if(timetableTitle.equals("")){
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_LONG).show();
                return;
            }
            ArrayList<TimetableData> t = db.getTimetable();
            for(TimetableData t1 : t){
                if(t1.timetable_title.equals(timetableTitle)){
                    Toast.makeText(getApplicationContext(), timetableTitle+" 이름의 시간표가 이미 있습니다!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            TimetableData timetableData = new TimetableData();
            timetableData.timetable_title = timetableTitle;
            db.insertTimetable(timetableData);

            t = db.getTimetable();
            long newTimetableNum = 1;
            for(TimetableData t1 : t){
                if(t1.timetable_title.equals(timetableTitle)){
                    newTimetableNum = t1.timetable_id;
                    break;
                }
            }
            Intent intent = new Intent();
            intent.putExtra("newTimetableNum", (int)newTimetableNum);
            setResult(3);
            finish();
        }
    }
}
