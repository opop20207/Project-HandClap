package com.projectHandClap.youruniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    int groupLayout = 0;
    int childLayout = 0;
    ArrayList<ExpandableGroup> dataList;
    LayoutInflater myinf = null;

    public ExpandableAdapter(Context context, int groupLayout, int childLayout, ArrayList<ExpandableGroup> dataList){
        this.context = context;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
        this.dataList = dataList;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            view = myinf.inflate(this.groupLayout, viewGroup, false);
        }
        TextView groupName = (TextView)view.findViewById(R.id.expandable_group_name);
        groupName.setText(dataList.get(i).groupName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            view = myinf.inflate(this.childLayout, viewGroup, false);
        }
        final TextView childName = (TextView)view.findViewById(R.id.expandable_child_name);
        final String t = dataList.get(i).child.get(i1);
        childName.setText(t);
        childName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                long res=0;
                ArrayList<TimetableData> temp = db.getTimetable();
                for(TimetableData t1 : temp){
                    if(t1.timetable_title.equals(t)){
                        res = t1.timetable_id;
                    }
                }
                ((MainActivity)context).timetableNum = res;
                ((MainActivity)context).addClassToLayout();
                ((MainActivity)context).drawerLayout.closeDrawer(((MainActivity)context).drawerView);
            }
        });
        return view;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return dataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return dataList.get(groupPosition).child.size();
    }

    @Override
    public ExpandableGroup getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }
}
