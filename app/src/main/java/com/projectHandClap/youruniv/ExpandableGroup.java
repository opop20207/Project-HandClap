package com.projectHandClap.youruniv;

import java.util.ArrayList;

public class ExpandableGroup {
    public ArrayList<String> child;
    public String groupName;

    public ExpandableGroup(String name){
        groupName = name;
        child = new ArrayList<String>();
    }
}
