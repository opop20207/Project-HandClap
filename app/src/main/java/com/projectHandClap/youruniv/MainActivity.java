package com.projectHandClap.youruniv;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ExpandableListView timetable_list;
    DrawerLayout drawerLayout;
    View drawerView;
    SettingData userSetting;
    long timetableNum;

    String [] tableDay = {"S","M","T","W","T","F","S","S"};
    int timeInterval = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        if(timetableNum==0) pragmaOnce();
        initView();
        setLayout();
        setDrawerLayout();
        addClassToLayout();
    }

    public void pragmaOnce(){
        DatabaseHelper db = new DatabaseHelper(this);
        SettingData settingData = db.getSetting();
        if(settingData == null) {
            TimetableData timetableData = new TimetableData();
            timetableData.timetable_title = "내 시간표";
            db.insertTimetable(timetableData);
            db.insertSetting(new SettingData(1,"1234567","900", "1800"));
        }
        userSetting = db.getSetting();
        timetableNum = userSetting.setting_main_timetable_id;
    }

    public void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
    }

    public void setLayout(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        TextView txv_timetable_title = (TextView)findViewById(R.id.txv_timetable_title);
        TimetableData timetableData = db.getTimetableOne((int)timetableNum);
        txv_timetable_title.setText(timetableData.timetable_title);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_timetable);
        tableLayout.removeAllViews();

        TableRow tableRow = new TableRow(getApplicationContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView temp = new TextView(getApplicationContext());
        temp.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        temp.setText("");
        tableRow.addView(temp);

        for(int i=1;i<=7;i++){
            if(userSetting.setting_day.contains(String.valueOf(i))){
                TextView tv = new TextView(getApplicationContext());
                tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 3f));
                tv.setText(tableDay[i]);
                tv.setGravity(Gravity.CENTER);
                tableRow.addView(tv);
            }
        }
        tableLayout.addView(tableRow);

        int stime = Integer.parseInt(userSetting.setting_stime);
        int etime = Integer.parseInt(userSetting.setting_etime);
        for(int t=stime; t<=etime;t+=timeInterval){
            TableRow tr = new TableRow(getApplicationContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

            for(int i=1;i<=7;i++){
                if(i==1){
                    TextView tvFirst = new TextView(getApplicationContext());
                    tvFirst.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    tvFirst.setText(Integer.toString(t));
                    tvFirst.setGravity(Gravity.CENTER);
                    tr.addView(tvFirst);
                }
                if(userSetting.setting_day.contains(String.valueOf(i))){
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 3f));
                    tv.setBackgroundResource(R.drawable.border);
                    String k = "row"+Integer.toString(t)+"day"+Integer.toString(i);
                    int a = getResources().getIdentifier(k,"id","com.projectHandClap.youruniv");
                    tv.setId(a);
                    tr.addView(tv);
                }
            }
            tableLayout.addView(tr);
        }
    }

    public void setDrawerLayout(){
        ArrayList<ExpandableGroup> dataList = new ArrayList<ExpandableGroup>();
        timetable_list = (ExpandableListView) findViewById(R.id.timetable_list);
        ExpandableGroup t = new ExpandableGroup("시간표");

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<TimetableData> timetableList = db.getTimetable();
        for(TimetableData t1 : timetableList){
            t.child.add(t1.timetable_title);
        }
        dataList.add(t);

        ExpandableAdapter adapter = new ExpandableAdapter(MainActivity.this, R.layout.group_row, R.layout.child_row, dataList);
        timetable_list.setAdapter(adapter);
    }

    public void addClassToLayout(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<ClassData> classDataList = db.getClassData((int)timetableNum);
        for(ClassData cd : classDataList){
            String title = cd.class_title;
            int stime = Integer.parseInt(cd.class_stime);
            int etime = Integer.parseInt(cd.class_etime);
            int day = Integer.parseInt(cd.class_day);
            for(int i=stime;i<=etime;i+=timeInterval){
                String k = "row"+Integer.toString(i)+"day"+Integer.toString(day);
                Log.e("!", k);
                int a = getResources().getIdentifier(k, "id", "com.projectHandClap.youruniv");
                TextView tv = (TextView) findViewById(a);
                //tv.setText(title);
                tv.setBackgroundResource(R.drawable.border_drawer);
            }
        }
    }

    public void onClickMainTo(View v){
        Intent intent = null;
        switch (v.getId()) {
            case R.id.open_add_class:
                intent = new Intent(MainActivity.this, AddClassActivity.class);
                intent.putExtra("timetableId", (int)timetableNum);
                startActivityForResult(intent, 1);
                break;
            case R.id.open_set:
                intent = new Intent(MainActivity.this, SetActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.open_recorder:
                intent = new Intent(MainActivity.this, RecorderActivity.class);
                startActivity(intent);
                break;
            case R.id.open_memo:
                intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
                break;
            case R.id.open_gallery:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                break;
            case R.id.open_calendar:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.open_add_timetable:
                intent = new Intent(MainActivity.this, AddTimetableActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.open_delete_timetable:
                intent = new Intent(MainActivity.this, DeleteTimetableActivity.class);
                startActivityForResult(intent, 4);
                break;
        }
    }

    public void onClickMain(View v){
        switch (v.getId()) {
            case R.id.open_drawer:
                drawerLayout.openDrawer(drawerView);
                break;
            case R.id.close_drawer:
                drawerLayout.closeDrawer(drawerView);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == 1){
            addClassToLayout();
        }else if(resultCode == 2){
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            userSetting = db.getSetting();
            timetableNum = userSetting.setting_main_timetable_id;
        }else if(resultCode == 3){
            timetableNum = getIntent().getIntExtra("newTimetableNum", 1);
            Log.e("!!", timetableNum+"!");
            drawerLayout.closeDrawer(drawerView);
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }else if(requestCode==4){
            drawerLayout.closeDrawer(drawerView);
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }
    }
}