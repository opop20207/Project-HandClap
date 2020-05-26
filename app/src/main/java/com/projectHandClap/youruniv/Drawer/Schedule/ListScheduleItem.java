package com.projectHandClap.youruniv.Drawer.Schedule;

import android.widget.TextView;
import android.widget.ToggleButton;

class ListScheduleItem{
    private Integer isDone;
    private TextView title;
    private TextView deadline;

    public ListScheduleItem(Integer isDone, TextView title, TextView deadline) {
        this.isDone = isDone;
        this.title = title;
        this.deadline = deadline;
    }

    public ListScheduleItem() {
    }

    public Integer getIsDone() {
        return isDone;
    }

    public void setIsDone(Integer isDone) {
        this.isDone = isDone;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDeadline() {
        return deadline;
    }

    public void setDeadline(TextView deadline) {
        this.deadline = deadline;
    }
}