package com.projectHandClap.youruniv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoDetailActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText etxt_add_memo_title, etxt_add_memo_memo;
    MemoData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        db = new DatabaseHelper(MemoDetailActivity.this);
        init();
    }

    public void init(){
        long mid = getIntent().getLongExtra("memoId", 1);
        mData = db.getMemoDataById((int)mid);

        etxt_add_memo_title = (EditText)findViewById(R.id.etxt_add_memo_title);
        etxt_add_memo_memo = (EditText)findViewById(R.id.etxt_add_memo_memo);

        etxt_add_memo_title.setText(mData.memo_title);
        etxt_add_memo_memo.setText(mData.memo_memo);
    }

    public void addToDatabaseMemo(){
        MemoData memoData = new MemoData();
        memoData.memo_id = mData.memo_id;
        memoData.memo_class_string = mData.memo_class_string;

        memoData.memo_title = etxt_add_memo_title.getText().toString();
        memoData.memo_memo = etxt_add_memo_memo.getText().toString();

        memoData.memo_time = mData.memo_time;

        db.updateMemo(memoData);
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
