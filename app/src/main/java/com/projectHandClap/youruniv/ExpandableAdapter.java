package com.projectHandClap.youruniv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

public class ExpandableAdapter
{
    public static class BaseExpandableAdapter extends BaseExpandableListAdapter {

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
    }
}
