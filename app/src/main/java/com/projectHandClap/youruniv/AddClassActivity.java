package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddClassActivity extends AppCompatActivity {
    EditText etxt_add_class_title, etxt_add_class_classroom, etxt_add_class_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
    }

    public void onClickAddClass(View v){
        switch (v.getId()){
            case R.id.btn_add_class_submit:
                break;
            case R.id.btn_add_class_cancel:
                break;
            case R.id.btn_add_class_time:
                break;
        }
    }
}
