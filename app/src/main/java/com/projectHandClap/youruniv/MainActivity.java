package com.projectHandClap.youruniv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

        Button btn_open_drawer = (Button)findViewById(R.id.open_drawer);

        btn_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }
        });
    }
}
