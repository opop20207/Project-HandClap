package com.projectHandClap.youruniv.Drawer.Schedule;

import com.projectHandClap.youruniv.Drawer.Schedule.ListScheduleItem;

import java.util.List;

/*
모델 클래스
 */

class ListSchedule{
    private String date;
    private List<ListScheduleItem> schedule;

    public ListSchedule(String date, List<ListScheduleItem> schedule) {
        this.date = date;
        this.schedule = schedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ListScheduleItem> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ListScheduleItem> schedule) {
        this.schedule = schedule;
    }
}
