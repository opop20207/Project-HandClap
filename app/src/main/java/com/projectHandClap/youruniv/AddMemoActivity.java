package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemoActivity extends AppCompatActivity {
    String classString;
    DatabaseHelper db;
    EditText etxt_add_memo_title, etxt_add_memo_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        db = new DatabaseHelper(AddMemoActivity.this);
        init();
    }

    public void init(){
        etxt_add_memo_title = (EditText)findViewById(R.id.etxt_add_memo_title);
        etxt_add_memo_memo = (EditText)findViewById(R.id.etxt_add_memo_memo);
    }

    public void addToDatabaseMemo(){
        MemoData memoData = new MemoData();
        memoData.memo_class_string = classString;

        memoData.memo_title = etxt_add_memo_title.getText().toString();
        memoData.memo_memo = etxt_add_memo_memo.getText().toString();

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmm");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String stringNow = sdfNow.format(date);
        memoData.memo_time = Long.parseLong(stringNow);

        db.insertMemo(memoData);
    }

    public void onClickAddMemo(View v){
        switch (v.getId()){
            case R.id.btn_add_memo_cancel:
                onBackPressed();
                break;
            case R.id.btn_add_memo_submit:
                addToDatabaseMemo();
                setResult(1);
                finish();
                break;
        }
    }
}
