package com.projectHandClap.youruniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        pragmaOnce();
        initView();
    }

    public void pragmaOnce(){
        DatabaseHelper db = new DatabaseHelper(this);
        SettingData settingData = db.getSetting();
        if(settingData.setting_id == -1) {
            db.insertTimetable("내 시간표");
            db.insertSetting(1,"1234567","900", "1800");
        }
    }

    public void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
    }

    public void onClickMainTo(View v){
        Intent intent = null;
        switch (v.getId()) {
            case R.id.open_add_class:
                intent = new Intent(MainActivity.this, AddClassActivity.class);
                break;
            case R.id.open_set:
                intent = new Intent(MainActivity.this, SetActivity.class);
                break;
            case R.id.open_schedule:
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
        }
    }
}