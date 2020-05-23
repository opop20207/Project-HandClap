package com.projectHandClap.youruniv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ExpandableListView timetable_list;
    DrawerLayout drawerLayout;
    View drawerView, bottomDrawerView;
    LinearLayout ll_bottom_drawer;
    DatabaseHelper db;

    SettingData userSetting;
    long timetableId;
    int selectedClassId;

    String [] tableDay = {"S","M","T","W","T","F","S","S"};
    int [] tableColor = {R.color.colorAccent, R.color.ttcolor1, R.color.ttcolor2, R.color.ttcolor3, R.color.ttcolor4, R.color.ttcolor5,
            R.color.ttcolor6, R.color.ttcolor7, R.color.ttcolor8, R.color.ttcolor9, R.color.ttcolor10};
    int timeInterval = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        init();
    }

    public void init(){
        if(timetableId == 0) pragmaOnce();
        initView();
        setLayout();
        setDrawerLayout();
        addClassToLayout();
    }

    public void pragmaOnce(){
        SettingData settingData = db.getSetting();
        if(settingData == null) {
            TimetableData timetableData = new TimetableData();
            timetableData.timetable_title = "내 시간표";
            db.insertTimetable(timetableData);
            db.insertSetting(new SettingData(1,"1234567","900", "1800"));
        }
        userSetting = db.getSetting();
        timetableId = userSetting.setting_main_timetable_id;
    }

    public void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        bottomDrawerView = (View)findViewById(R.id.bottomDrawer);
    }

    public void setLayout(){
        TextView txv_timetable_title = (TextView)findViewById(R.id.txv_timetable_title);
        TimetableData timetableData = db.getTimetableOne((int)timetableId);
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

        ArrayList<TimetableData> timetableList = db.getTimetable();
        for(TimetableData t1 : timetableList){
            t.child.add(t1.timetable_title);
        }
        dataList.add(t);

        ExpandableAdapter adapter = new ExpandableAdapter(MainActivity.this, R.layout.group_row, R.layout.child_row, dataList);
        timetable_list.setAdapter(adapter);
    }

    public void addClassToLayout(){
        ArrayList<ClassData> classDataList = db.getClassData((int)timetableId);
        for(ClassData cd : classDataList){
            String title = cd.class_title;
            int stime = Integer.parseInt(cd.class_stime);
            int etime = Integer.parseInt(cd.class_etime);
            int day = Integer.parseInt(cd.class_day);
            for(int i=stime;i<=etime;i+=timeInterval){
                if(stime<Integer.parseInt(userSetting.setting_stime) || etime>Integer.parseInt(userSetting.setting_etime)) continue;
                String k = "row"+Integer.toString(i)+"day"+Integer.toString(day);
                Log.e("!", k);
                int a = getResources().getIdentifier(k, "id", "com.projectHandClap.youruniv");
                final TextView tv = (TextView) findViewById(a);
                tv.setOnClickListener(new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedClassId = tv.getId();
                        drawerLayout.openDrawer(bottomDrawerView);
                    }
                });
                //tv.setText(title);
                tv.setBackgroundColor(getResources().getColor(tableColor[Integer.parseInt(cd.class_color)]));
            }
        }
    }

    public void onClickMainTo(View v){
        Intent intent = null;
        switch (v.getId()) {
            case R.id.open_add_class:
                intent = new Intent(MainActivity.this, AddClassActivity.class);
                intent.putExtra("timetableId", (int)timetableId);
                startActivityForResult(intent, 1);
                break;
            case R.id.open_set:
                intent = new Intent(MainActivity.this, SetActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.open_add_timetable:
                intent = new Intent(MainActivity.this, AddTimetableActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.open_delete_timetable:
                intent = new Intent(MainActivity.this, DeleteTimetableActivity.class);
                startActivityForResult(intent, 4);
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
            case R.id.open_schedule:
                intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);
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
            //open_add_class
            addClassToLayout();
        }else if(resultCode == 2){
            //open_set
            userSetting = db.getSetting();
            timetableId = data.getIntExtra("newTimetableId", 1);
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }else if(resultCode == 3){
            //open_add_timetable
            timetableId = data.getIntExtra("newTimetableId", 1);
            Log.e("ttI", timetableId+"!");
            drawerLayout.closeDrawer(drawerView);
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }else if(requestCode==4){
            //open_delete_timetable
            drawerLayout.closeDrawer(drawerView);
            TimetableData chk = db.getTimetableOne((int)timetableId);
            if(chk==null){
                timetableId = userSetting.setting_main_timetable_id;
            }
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }
    }
}