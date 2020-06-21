package com.projectHandClap.youruniv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeleteTimetableActivity extends AppCompatActivity {
    LinearLayout ll_delete_timetable;

    DatabaseHelper db;
    SettingData userSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_timetable);

        db = new DatabaseHelper(getApplicationContext());
        userSetting = db.getSetting();
        init();
        setLayout();
    }

    public void init(){
        ll_delete_timetable = (LinearLayout) findViewById(R.id.ll_delete_timetable);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setLayout(){
        ll_delete_timetable.removeAllViews();
        final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<TimetableData> t = db.getTimetable();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        for(TimetableData t1 : t){
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button btnDelete = new Button(getApplicationContext());
            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));
            TextView txv = new TextView(getApplicationContext());
            txv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            txv.setText(t1.timetable_title);
            txv.setGravity(Gravity.CENTER);
            txv.setTypeface(getResources().getFont(R.font.font));
            btnDelete.setText("삭제");
            btnDelete.setTypeface(getResources().getFont(R.font.font));
            final int did = t1.timetable_id;
            btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(did == userSetting.setting_main_timetable_id){
                        Toast.makeText(getApplicationContext(), "메인 시간표로 설정된 시간표는 삭제할 수 없습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ArrayList<ClassData> clist = db.getClassData(did);
                    for(ClassData cd : clist){
                        db.deleteScheduleByClassString(cd.class_string);
                        db.deleteGalleryByClassString(cd.class_string);
                        db.deleteRecordByClassString(cd.class_string);
                        db.deleteMemoByClassString(cd.class_string);
                    }
                    db.deleteTimetable((int)did);
                    db.deleteClassData((int)did);
                    setLayout();
                }
            });
            linearLayout.addView(btnDelete);
            linearLayout.addView(txv);

            ll_delete_timetable.addView(linearLayout);
        }
    }
}
