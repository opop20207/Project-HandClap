package com.projectHandClap.youruniv.Drawer.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.projectHandClap.youruniv.Drawer.Schedule.ListSchedule;
import com.projectHandClap.youruniv.R;

import java.util.List;

public class ListScheduleAdapter extends BaseAdapter{
    private Context mContext;
    private List<ListSchedule> mData;

    public ListScheduleAdapter(Context context, List<ListSchedule> data){
        mContext = context;
        mData = data;
    }

    public ListScheduleAdapter() {

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) { return mData.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.activity_list_schedule, parent, false);
        }

        TextView txv_date = (TextView)convertView.findViewById(R.id.txv_schedule_date);
        ListView listSchedule = (ListView)convertView.findViewById(R.id.list_schedule_by_date);

        ListSchedule schedule = mData.get(position);

        txv_date.setText(schedule.getDate());
        listSchedule.setAdapter((ListAdapter) schedule.getSchedule());

        return convertView;
    }
}