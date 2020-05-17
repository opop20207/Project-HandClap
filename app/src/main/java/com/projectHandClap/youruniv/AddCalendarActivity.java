package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddCalendarActivity extends AppCompatActivity {
    private EditText schedule_name;
    private EditText schedule_content;
    private EditText schedule_date;
    private EditText schedule_time_start;
    private EditText schedule_time_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);

        schedule_content = findViewById(R.id.schedule_content);
        schedule_name = findViewById(R.id.schedule_name);
        schedule_date = findViewById(R.id.schedule_date);
        schedule_time_end = findViewById(R.id.schedule_time_end);
        schedule_time_start = findViewById(R.id.schedule_time_start);
    }
}
