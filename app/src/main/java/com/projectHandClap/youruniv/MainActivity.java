package com.projectHandClap.youruniv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.projectHandClap.youruniv.Drawer.Gallery.GalleryActivity;
import com.projectHandClap.youruniv.Drawer.MemoActivity;
import com.projectHandClap.youruniv.Drawer.RecorderActivity;
import com.projectHandClap.youruniv.Drawer.Schedule.ScheduleActivity;
import com.projectHandClap.youruniv.ViewPager.ViewPagerActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public long timetableId;
    ExpandableListView timetable_list;
    DrawerLayout drawerLayout;
    View drawerView;
    SettingData userSetting;

    Button bottomSheetDialog_btnGallery, bottomSheetDialog_btnDetail, bottomSheetDialog_btnDelete;
    Button bottomSheetDialog_btnRecord, bottomSheetDialog_btnMemo, bottomSheetDialog_btnSchedule;
    TextView bottomSheetDialog_txvTitle;
    DatabaseHelper db;

    int [] tableColor = {R.color.colorAccent, R.color.ttcolor1, R.color.ttcolor2, R.color.ttcolor3, R.color.ttcolor4, R.color.ttcolor5,
            R.color.ttcolor6, R.color.ttcolor7, R.color.ttcolor8, R.color.ttcolor9, R.color.ttcolor10};
    String [] tableDay = {"S","M","T","W","T","F","S","S"};
    int timeInterval = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        init();
    }

    public void init(){
        if(timetableId==0) pragmaOnce();
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
    }

    public void setLayout(){
        final TextView txv_timetable_title = (TextView)findViewById(R.id.txv_timetable_title);
        final TimetableData timetableData = db.getTimetableOne((int)timetableId);
        txv_timetable_title.setText(timetableData.timetable_title);
        txv_timetable_title.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(MainActivity.this);
                editText.setText(timetableData.timetable_title);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                dlg.setTitle("시간표 이름 변경");
                dlg.setView(editText);
                dlg.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.updateTimetable(new TimetableData(timetableData.timetable_id, editText.getText().toString()));

                                setLayout();
                                setDrawerLayout();
                                addClassToLayout();
                            }
                    });
                dlg.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dlg.show();

            }
        });

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

    public int getPixelFromDips(float pixels){
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels*scale+0.5f);
    }

    public void setDrawerLayout(){
        timetable_list = (ExpandableListView) findViewById(R.id.timetable_list);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = 800;
        timetable_list.setIndicatorBounds(width-getPixelFromDips(50),width-getPixelFromDips(10));
        timetable_list.setChildDivider(getResources().getDrawable(R.drawable.border));
        ArrayList<ExpandableGroup> dataList = new ArrayList<ExpandableGroup>();
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

                final ClassData fcd = cd;

                TextView tv = (TextView) findViewById(a);
                //tv.setText(title);
                tv.setBackgroundColor(getResources().getColor(tableColor[Integer.parseInt(cd.class_color)]));
                tv.setOnClickListener(new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int cid = (int)fcd.class_id;
                        final String cstr = fcd.class_string;
                        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        dialog.setContentView(R.layout.bottomsheetdialog);

                        bottomSheetDialog_btnDetail = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnDetail);
                        bottomSheetDialog_btnDelete = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnDelete);
                        bottomSheetDialog_btnGallery = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnGallery);
                        bottomSheetDialog_btnRecord = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnRecord);
                        bottomSheetDialog_btnMemo = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnMemo);
                        bottomSheetDialog_btnSchedule = (Button)dialog.findViewById(R.id.bottomSheetDialog_btnSchedule);
                        bottomSheetDialog_txvTitle = (TextView)dialog.findViewById(R.id.bottomSheetDialog_txvTitle);

                        bottomSheetDialog_txvTitle.setText(fcd.class_title);
                        bottomSheetDialog_btnDetail.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ClassDetailActivity.class);
                                intent.putExtra("classDataId", cid);
                                startActivity(intent);
                            }
                        });
                        bottomSheetDialog_btnDelete.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.deleteClassDataOne(cstr);
                                setLayout();
                                setDrawerLayout();
                                addClassToLayout();
                                dialog.cancel();
                            }
                        });
                        bottomSheetDialog_btnGallery.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                                intent.putExtra("classDataId", cid);
                                startActivity(intent);
                            }
                        });
                        bottomSheetDialog_btnRecord.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                                intent.putExtra("classDataId", cid);
                                startActivity(intent);
                            }
                        });
                        bottomSheetDialog_btnMemo.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                                intent.putExtra("classDataId", cid);
                                startActivity(intent);
                            }
                        });
                        bottomSheetDialog_btnSchedule.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                                intent.putExtra("classDataId", cid);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                    }
                });
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
            addClassToLayout();
        }else if(resultCode == 2){
            userSetting = db.getSetting();
            timetableId = data.getIntExtra("newTimetableId", 1);
            setLayout();
            setDrawerLayout();
            addClassToLayout();
        }else if(resultCode == 3){
            timetableId = data.getIntExtra("newTimetableId", 1);
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