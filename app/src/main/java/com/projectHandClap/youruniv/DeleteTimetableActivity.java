package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteTimetableActivity extends AppCompatActivity {
    LinearLayout ll_delete_timetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_timetable);

        init();
        setLayout();
    }

    public void init(){
        ll_delete_timetable = (LinearLayout) findViewById(R.id.ll_delete_timetable);
    }

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
            btnDelete.setText("삭제");
            final long did = t1.timetable_id;
            btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<TimetableData> tt = db.getTimetable();
                    if(tt.size()==1){
                        Toast.makeText(getApplicationContext(), "최소 1개의 시간표가 필요합니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.deleteTimetable((int)did);
                    setLayout();
                }
            });
            linearLayout.addView(btnDelete);
            linearLayout.addView(txv);

            ll_delete_timetable.addView(linearLayout);
        }
    }
}
