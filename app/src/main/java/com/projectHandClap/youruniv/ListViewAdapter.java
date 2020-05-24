package com.projectHandClap.youruniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter(){

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ToggleButton tbtn_schedule_done = (ToggleButton) convertView.findViewById(R.id.tbtn_schedule_done);
        TextView txv_schedule_title = (TextView)convertView.findViewById(R.id.txv_schedule_title);
        TextView txv_Schedule_deadline = (TextView)convertView.findViewById(R.id.txv_schedule_deadline);

        ListViewItem listViewItem = listViewItemList.get(position);

        if(listViewItem.getIsDone().isChecked())    tbtn_schedule_done.setChecked(true);
        else tbtn_schedule_done.setChecked(false);

        txv_schedule_title.setText(listViewItem.getTittle());
        txv_Schedule_deadline.setText(listViewItem.getDeadline());

        return convertView;
    }
}