package com.projectHandClap.youruniv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class AddTimetableActivity extends Activity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_timetable);
        db = new DatabaseHelper(getApplicationContext());

    }

    public void onClickAddTimetable(View v){
        if(v.getId()==R.id.btn_add_timetable_submit){
            EditText editText = (EditText)findViewById(R.id.etxt_add_timetable_title);
            String timetableTitle = editText.getText().toString();
            if(timetableTitle.equals("")){
                //no name error
                Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_LONG).show();
                return;
            }

            ArrayList<TimetableData> t = db.getTimetable();
            for(TimetableData t1 : t){
                if(t1.timetable_title.equals(timetableTitle)){
                    //name error
                    Toast.makeText(getApplicationContext(), timetableTitle+" 이름의 시간표가 이미 있습니다!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            TimetableData timetableData = new TimetableData();
            timetableData.timetable_title = timetableTitle;
            db.insertTimetable(timetableData);

            t = db.getTimetable();
            long newTimetableId = 1;
            for(TimetableData t1 : t){
                if(t1.timetable_title.equals(timetableTitle)){
                    newTimetableId = t1.timetable_id;
                    break;
                }
            }

            Intent intent = new Intent();
            intent.putExtra("newTimetableId", (int)newTimetableId);
            setResult(3, intent);
            finish();
        }
    }
}
