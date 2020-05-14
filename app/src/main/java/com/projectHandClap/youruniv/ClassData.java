package com.projectHandClap.youruniv;

public class ClassData {
    long class_id;
    int class_timetable_id;
    String class_title;
    String class_day;
    String class_stime;
    String class_etime;

    public ClassData(){

    }

    public ClassData(int class_timetable_id, String class_title, String class_day, String class_stime, String class_etime) {
        this.class_timetable_id = class_timetable_id;
        this.class_title = class_title;
        this.class_day = class_day;
        this.class_stime = class_stime;
        this.class_etime = class_etime;
    }
}
