package com.projectHandClap.youruniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private  DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        Button open_recorder = (Button)findViewById(R.id.open_recorder);
        Button open_calendar = (Button)findViewById(R.id.open_calendar);
        Button open_gallery = (Button)findViewById(R.id.open_gallery);
        Button close_drawer = (Button)findViewById(R.id.close_drawer);
        Button open_drawer = (Button)findViewById(R.id.open_drawer);
        Button open_memo = (Button)findViewById(R.id.open_memo);
        Button open_set = (Button)findViewById(R.id.open_set);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.open_drawer:
                        drawerLayout.openDrawer(drawerView);
                        break;

                    case R.id.close_drawer:
                        drawerLayout.closeDrawer(drawerView);
                        break;

                    case R.id.open_set:
                        Intent intent_set = new Intent(MainActivity.this, SetActivity.class);
                        startActivity(intent_set);
                        break;

                    case R.id.open_schedule:
                        break;

                    case R.id.open_recorder:
                        Intent intent_recorder = new Intent(MainActivity.this, RecorderActivity.class);
                        startActivity(intent_recorder);
                        break;

                    case R.id.open_memo:
                        Intent intent_memo = new Intent(MainActivity.this, MemoActivity.class);
                        startActivity(intent_memo);
                        break;

                    case R.id.open_gallery:
                        Intent intent_gallery = new Intent(MainActivity.this, GalleryActivity.class);
                        startActivity(intent_gallery);
                        break;

                    case R.id.open_calendar:
                        Intent intent_calendar = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent_calendar);
                        break;
                }
            }
        };

        open_calendar.setOnClickListener(listener);
        open_recorder.setOnClickListener(listener);
        open_gallery.setOnClickListener(listener);
        close_drawer.setOnClickListener(listener);
        open_drawer.setOnClickListener(listener);
        open_memo.setOnClickListener(listener);
        open_set.setOnClickListener(listener);
    }
}