package com.projectHandClap.youruniv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private ArrayList<String> childListContent = null;

    private DrawerLayout drawerLayout;
    private View drawerView;
    SettingData userSetting;

    String [] tableDay = {"S","M","T","W","T","F","S","S"};
    int timeInterval = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        groupList = new ArrayList<String>();
        childList = new ArrayList<ArrayList<String>>();
        childListContent = new ArrayList<String>();

        groupList.add("시간표");

        // DB에서 시간표 목록 받아와서 ChildListContent에 추가하기
        childListContent.add("");

        childList.add(childListContent);

        timetable_list.setAdapter(new ExpandableAdapter(this, groupList, childList));
    }
/*
    public class BaseExpandableAdapter extends BaseExpandableListAdapter{

        private ArrayList<String> groupList = null;
        private ArrayList<ArrayList<String>> childList = null;

        BaseExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){
            super();
            this.groupList = groupList;
            this.childList = childList;
        }

        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() { return true; }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
    }*/

    public void init(){
        pragmaOnce();
        initView();
        setLayout();
        addClassToLayout();
    }

    public void pragmaOnce(){
        DatabaseHelper db = new DatabaseHelper(this);
        db.restart();
        SettingData settingData = db.getSetting();
        if(settingData == null) {
            TimetableData timetableData = new TimetableData();
            timetableData.timetable_title = "내 시간표";
            db.insertTimetable(timetableData);
            db.insertSetting(new SettingData(1,"1234567","900", "1800"));
        }
        userSetting = db.getSetting();
    }

    public void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
    }

    public void setLayout(){
        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_timetable);
        tableLayout.removeAllViews();

        TableRow tableRow = new TableRow(getApplicationContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView temp = new TextView(getApplicationContext());
        temp.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        temp.setText("");
        tableRow.addView(temp);

        for(int i=1;i<=7;i++){
            if(userSetting.setting_day.contains(String.valueOf(i))){
                TextView tv = new TextView(getApplicationContext());
                tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
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
                    tvFirst.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    tvFirst.setText(Integer.toString(t));
                    tvFirst.setGravity(Gravity.CENTER);
                    tr.addView(tvFirst);
                }
                if(userSetting.setting_day.contains(String.valueOf(i))){
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 3f));
                    tv.setBackgroundResource(R.drawable.border);
                    String k = "row"+Integer.toString(t)+"day"+Integer.toString(i);
                    int a = getResources().getIdentifier(k,"id","com.projectHandClap.youruniv");
                    tv.setId(a);
                    tr.addView(tv);
                }
            }
            tableLayout.addView(tr);
        }

        timetable_list = (ExpandableListView) findViewById(R.id.timetable_list);
    }

    private ExpandableListView timetable_list;

    public void addClassToLayout(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<ClassData> classDataList = db.getClassData(userSetting.setting_main_timetable_id);
        for(ClassData cd : classDataList){
            String title = cd.class_title;
            int stime = Integer.parseInt(cd.class_stime);
            int etime = Integer.parseInt(cd.class_etime);
            int day = Integer.parseInt(cd.class_day);
            for(int i=stime;i<=etime;i+=timeInterval){
                String k = "row"+Integer.toString(i)+"day"+Integer.toString(day);
                int a = getResources().getIdentifier(k, "id", "com.projectHandClap.youruniv");
                TextView tv = (TextView) findViewById(a);
                tv.setText(title);
            }
        }
    }

    public void onClickMainTo(View v){
        Intent intent = null;
        switch (v.getId()) {
            case R.id.open_add_class:
                intent = new Intent(MainActivity.this, AddClassActivity.class);
                intent.putExtra("timetableId", userSetting.setting_main_timetable_id);
                break;
            case R.id.open_set:
                intent = new Intent(MainActivity.this, SetActivity.class);
                break;
            case R.id.open_recorder:
                intent = new Intent(MainActivity.this, RecorderActivity.class);
                break;
            case R.id.open_memo:
                intent = new Intent(MainActivity.this, MemoActivity.class);
                break;
            case R.id.open_gallery:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                break;
            case R.id.open_calendar:
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void onClickMain(View v){
        switch (v.getId()) {
            case R.id.open_drawer:
                drawerLayout.openDrawer(drawerView);
                break;
            case R.id.close_drawer:
                drawerLayout.closeDrawer(drawerView);
                break;

            case R.id.timetable_list:
                break;
        }
    }
}