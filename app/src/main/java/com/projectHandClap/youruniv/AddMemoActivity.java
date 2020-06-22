package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMemoActivity extends AppCompatActivity {
    String classString;
    DatabaseHelper db;
    EditText etxt_add_memo_title, etxt_add_memo_memo;

    String cstr;
    int cid;
    TextView txv_add_memo_class;
    Button btn_add_memo_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        db = new DatabaseHelper(AddMemoActivity.this);
        init();initIndex();
    }


    public void initIndex(){
        txv_add_memo_class = (TextView)findViewById(R.id.txv_add_memo_class);
        btn_add_memo_class = (Button)findViewById(R.id.btn_add_memo_class);

        cid = getIntent().getIntExtra("classDataId", -1);
        cstr = getIntent().getStringExtra("classString");
        if(cstr==null) cstr = "Default String";
        ClassData cd = db.getClassDataOneById(cid);
        if(cid==-1){
            txv_add_memo_class.setText("-");
        }else{
            txv_add_memo_class.setText(cd.class_title);
        }

        final ArrayList<ClassData> clist = db.getClassDataAll();
        final ArrayList<String> cstrlist = new ArrayList<>();
        final ArrayList<String> cstrTempList = new ArrayList<>();
        final ArrayList<Integer> cstrIdList = new ArrayList<>();
        cstrlist.add("All Classes");
        for(ClassData cdata : clist) {
            if(cstrTempList.contains(cdata.class_string)) continue;
            cstrTempList.add(cdata.class_string);
            cstrlist.add(cdata.class_title);
            cstrIdList.add(cdata.class_id);
        }

        final CharSequence[] classItem = cstrlist.toArray(new String[cstrlist.size()]);
        btn_add_memo_class.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMemoActivity.this);
                builder.setItems(classItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            txv_add_memo_class.setText("-");
                            cid = -1;
                            cstr = "Default String";
                            return;
                        }
                        i--;
                        String selectedClassTitle = cstrlist.get(i);
                        String selectedClassString = cstrTempList.get(i);
                        txv_add_memo_class.setText(selectedClassTitle);
                        cid = (int)cstrIdList.get(i);
                        cstr = selectedClassString;
                    }
                });
                builder.create().show();
            }
        });
    }

    public void init(){
        etxt_add_memo_title = (EditText)findViewById(R.id.etxt_add_memo_title);
        etxt_add_memo_memo = (EditText)findViewById(R.id.etxt_add_memo_memo);
    }

    public void addToDatabaseMemo(){
        MemoData memoData = new MemoData();
        memoData.memo_class_string = cstr;

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
