package com.projectHandClap.youruniv.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projectHandClap.youruniv.AddMemoActivity;
import com.projectHandClap.youruniv.DatabaseHelper;
import com.projectHandClap.youruniv.Drawer.Schedule.AddScheduleActivity;
import com.projectHandClap.youruniv.MemoData;
import com.projectHandClap.youruniv.MemoDetailActivity;
import com.projectHandClap.youruniv.R;
import com.projectHandClap.youruniv.ScheduleData;

import java.util.ArrayList;


public class Fragment_Memo extends Fragment {
    ViewPagerActivity viewPagerActivity;
    ViewGroup viewGroup;
    LinearLayout layoutMemo;
    DatabaseHelper db;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_memo, container,false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        init();

        return viewGroup;
    }

    public void init(){
        Button fab = viewGroup.findViewById(R.id.btn_add_memo);

        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddMemoActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        layoutMemo = (LinearLayout)viewGroup.findViewById(R.id.layout_memo);
        setLayout();
    }

    public void setLayout(){
        layoutMemo.removeAllViews();

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        Typeface typeface = getResources().getFont(R.font.font);

        long nowtime = 0;

        ArrayList<MemoData> memoData = db.getMemo();
        Log.e("!!!", memoData.size()+"!@");
        for(MemoData m : memoData){
            long t = m.memo_time%10000;
            long d = (m.memo_time/10000);
            String st = String.valueOf(t);
            String sd = String.valueOf(d);
            final MemoData fm = m;

            if(nowtime != d){
                nowtime = d;

                TextView txvDate = new TextView(mContext);

                txvDate.setText(String.format(sd));
                txvDate.setLayoutParams(layoutParams);
                txvDate.setTextSize(15);
                txvDate.setTypeface(typeface);
                txvDate.getTypeface();
                txvDate.setPadding(30,50,15, 0);
                layoutMemo.addView(txvDate);
            }

            LinearLayout ll = new LinearLayout(mContext);
            ll.setLayoutParams(layoutParams);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView txvTitle = new TextView(mContext);
            txvTitle.setLayoutParams(layoutParams);
            txvTitle.setText(m.memo_title);
            txvTitle.setTypeface(typeface);
            txvTitle.setPadding(100, 15,15,0);
            txvTitle.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            Button btnDetail = new Button(mContext);
            btnDetail.setText("상세");
            btnDetail.setTypeface(typeface);
            btnDetail.setBackgroundColor(Color.TRANSPARENT);
            btnDetail.setPadding(0, 15, 10, 0);
            btnDetail.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2));
            btnDetail.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MemoDetailActivity.class);
                    intent.putExtra("memoId", fm.memo_id);
                    startActivity(intent);
                }
            });

            Button btnDelete = new Button(mContext);
            btnDelete.setText("삭제");
            btnDelete.setTypeface(typeface);
            btnDelete.setBackgroundColor(Color.TRANSPARENT);
            btnDelete.setPadding(0, 15, 10, 0);
            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2));
            btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteMemo(fm);
                    setLayout();
                }
            });

            ll.addView(txvTitle);
            ll.addView(btnDetail);
            ll.addView(btnDelete);
            layoutMemo.addView(ll);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==1){
            setLayout();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        viewPagerActivity = (ViewPagerActivity) getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        viewPagerActivity = null;
    }

    public Fragment_Memo(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}