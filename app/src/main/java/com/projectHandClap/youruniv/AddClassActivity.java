package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddClassActivity extends AppCompatActivity {
    EditText etxt_add_class_title, etxt_add_class_place, etxt_add_class_time;
    EditText etxt_add_class_alarm, etxt_add_class_professor, etxt_add_class_memo;

    int timetableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        init();
    }

    public void init(){
        initView();
        timetableId = getIntent().getIntExtra("timetableId", 1);
    }

    public void initView(){
        etxt_add_class_title = (EditText) findViewById(R.id.etxt_add_class_title);
        etxt_add_class_place = (EditText) findViewById(R.id.etxt_add_class_place);
        etxt_add_class_professor = (EditText)findViewById(R.id.etxt_add_class_professor);
        etxt_add_class_memo = (EditText) findViewById(R.id.etxt_add_class_memo);
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

                break;
        }
    }

    public void addToDatabase_Class(){
        ClassData classData = new ClassData();
        classData.class_timetable_id = timetableId;
        classData.class_title = etxt_add_class_title.getText().toString();
        classData.class_place = etxt_add_class_place.getText().toString();
        classData.class_professor = etxt_add_class_professor.getText().toString();
        classData.class_day = etxt_add_class_time.getText().toString();
        classData.class_stime = etxt_add_class_time.getText().toString();
        classData.class_etime = etxt_add_class_time.getText().toString();
        classData.class_color = "1";
        classData.class_memo = etxt_add_class_memo.getText().toString();
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.insertClassData(classData);
    }
}
