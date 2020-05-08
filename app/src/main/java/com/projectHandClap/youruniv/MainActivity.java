package com.projectHandClap.youruniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private  DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        drawerLayout.addDrawerListener(listener);

        // drawer 열기
        Button btn_open_drawer = (Button)findViewById(R.id.open_drawer);
        btn_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener()
    {
        @Override
        public void onDrawerClosed(@NonNull View view) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View view) {
            // drawer 닫기
            Button btn_close = (Button)findViewById(R.id.close_drawer);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.closeDrawers();
                }
            });

            // set 열기
            Button btn_set = (Button)findViewById(R.id.open_set);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    startActivity(intent);
                }
            });

            // schedule 열기
            Button btn_schedule = (Button)findViewById(R.id.open_schedule);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                }
            });

            // gallery 열기
            Button btn_gallery = (Button)findViewById(R.id.open_gallery);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    startActivity(intent);
                }
            });

            // recorder 열기
            Button btn_recorder = (Button)findViewById(R.id.open_recorder);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    startActivity(intent);
                }
            });

            // memo 열기
            Button btn_memo = (Button)findViewById(R.id.open_memo);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    startActivity(intent);
                }
            });

            // calendar 열기
            Button btn_calendar = (Button)findViewById(R.id.open_calendar);
            btn_set.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), SetActivity.class);
                    startActivity(intent);
                }
            });
        }
    };
}