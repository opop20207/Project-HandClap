package com.projectHandClap.youruniv.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.projectHandClap.youruniv.ClassData;
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

    TextView txv_memo_index_date, txv_memo_index_class;
    Button btn_memo_index_date, btn_memo_index_class;
    ArrayList<Long> dateList;
    long y=0, m=0, d=0;
    int cid;
    String cstr;
    String selDateString;
    long selDateLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_memo, container,false);

        mContext = viewGroup.getContext();
        db = new DatabaseHelper(mContext);
        cid = viewPagerActivity.cid;
        cstr = viewPagerActivity.cstr;
        dateList = new ArrayList<>();
        selDateString=null;
        selDateLong = -1;
        init();

        return viewGroup;
    }

    public void initIndex(){
        txv_memo_index_class = (TextView)viewGroup.findViewById(R.id.txv_memo_index_class);
        txv_memo_index_date = (TextView)viewGroup.findViewById(R.id.txv_memo_index_date);
        btn_memo_index_class = (Button)viewGroup.findViewById(R.id.btn_memo_index_class);
        btn_memo_index_date = (Button)viewGroup.findViewById(R.id.btn_memo_index_date);

        if(cid!=-1){
            ClassData cd = db.getClassDataOneById(cid);
            txv_memo_index_class.setText(cd.class_title);
        }

        final ArrayList<ClassData> clist = db.getClassDataAll();
        final ArrayList<ClassData> selectedClist = new ArrayList<>();
        final ArrayList<String> cstrlist = new ArrayList<>();
        final ArrayList<String> chklist = new ArrayList<>();
        cstrlist.add("All Classes");
        for(ClassData cdata : clist) {
            if(chklist.contains(cdata.class_string)) continue;
            chklist.add(cdata.class_string);
            cstrlist.add(cdata.class_title);
            selectedClist.add(cdata);
        }

        final CharSequence[] classItem = cstrlist.toArray(new String[cstrlist.size()]);

        btn_memo_index_class.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setItems(classItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("!!@", "!"+i);
                        if(i==0){
                            txv_memo_index_class.setText("-");
                            cid = -1;
                            cstr = "Default String";
                            viewPagerActivity.cid = cid;
                            viewPagerActivity.cstr = cstr;
                            setLayout();
                            return;
                        }
                        i--;
                        String selectedClassTitle = selectedClist.get(i).class_title;
                        String selectedClassString = selectedClist.get(i).class_string;
                        Log.e("!!@", selectedClassTitle);
                        txv_memo_index_class.setText("ㅁ");
                        cid = (int)selectedClist.get(i).class_id;
                        cstr = selectedClassString;
                        viewPagerActivity.cid = cid;
                        viewPagerActivity.cstr = cstr;
                        setLayout();
                    }
                });
                builder.create().show();
            }
        });

        final ArrayList<String> dateStrList = new ArrayList<>();
        dateStrList.add("All date");
        for(long t : dateList){
            dateStrList.add(String.valueOf(t));
        }

        final CharSequence[] dateItem = dateStrList.toArray(new String[dateStrList.size()]);
        btn_memo_index_date.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(dateItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            txv_memo_index_date.setText("-");
                            selDateString = null;
                            selDateLong = -1;
                            setLayout();
                            return;
                        }
                        i--;
                        String selectedDateString = dateStrList.get(i);
                        long selectedDateLong = Long.parseLong(selectedDateString);
                        txv_memo_index_date.setText(selectedDateString);
                        selDateString = selectedDateString;
                        selDateLong = selectedDateLong;
                        setLayout();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void init(){
        Button fab = viewGroup.findViewById(R.id.btn_add_memo);

        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, AddMemoActivity.class);
                intent.putExtra("classDataId", cid);
                intent.putExtra("classString", cstr);
                startActivityForResult(intent, 1);
            }
        });

        layoutMemo = (LinearLayout)viewGroup.findViewById(R.id.layout_memo);
        setLayout();
    }

    public void setLayout(){
        layoutMemo.removeAllViews();
        dateList = new ArrayList<>();

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        Typeface typeface = getResources().getFont(R.font.font);

        long nowtime = 0;
        ArrayList<MemoData> memoData;
        if(cid==-1){
            memoData = db.getMemo();
        }else{
            memoData = db.getMemoByClassString(cstr);
        }
        Log.e("!!!", memoData.size()+"!@");
        for(MemoData m : memoData){
            long t = m.memo_time%10000;
            long d = (m.memo_time/10000);

            long year=d/10000;
            long month=(d%10000)/100;
            long day=(d%10000)%100;

            String sd=String.valueOf(year)+"년 "+String.valueOf(month)+"월 "+String.valueOf(day)+"일";

            final MemoData fm = m;

            if(nowtime != d){
                nowtime = d;


                dateList.add(d);
                if(selDateLong!=-1 && selDateLong!=d){
                    continue;
                }
                TextView txvDate = new TextView(mContext);
                txvDate.setText(String.format(sd));
                txvDate.setLayoutParams(layoutParams);
                txvDate.setTextSize(15);
                txvDate.setTypeface(typeface);
                txvDate.getTypeface();
                txvDate.setPadding(30,50,15, 0);
                layoutMemo.addView(txvDate);
            }else if(selDateLong!=-1 && selDateLong!=d){
                continue;
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
                    intent.putExtra("classDataId", cid);
                    intent.putExtra("classString", cstr);
                    startActivityForResult(intent, 1);
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
        initIndex();
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