package com.projectHandClap.youruniv;

public class TimetableData {
    long timetable_id;
    String timetable_title;

    public TimetableData(){
        timetable_id = 0;
    }

    public TimetableData(long timetable_id, String timetable_title) {
        this.timetable_id = timetable_id;
        this.timetable_title = timetable_title;
    }
}
