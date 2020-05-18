package com.projectHandClap.youruniv;

public class ClassData {
    long class_id;
    long class_timetable_id;
    String class_title;
    String class_place;
    String class_professor;
    String class_day;
    String class_stime;
    String class_etime;
    String class_alarm;
    String class_color;
    String class_memo;


    public ClassData(){

    }

    public ClassData(long class_id, long class_timetable_id, String class_title, String class_place, String class_professor, String class_day, String class_stime, String class_etime, String class_alarm, String class_color, String class_memo) {
        this.class_id = class_id;
        this.class_timetable_id = class_timetable_id;
        this.class_title = class_title;
        this.class_place = class_place;
        this.class_professor = class_professor;
        this.class_day = class_day;
        this.class_stime = class_stime;
        this.class_etime = class_etime;
        this.class_alarm = class_alarm;
        this.class_color = class_color;
        this.class_memo = class_memo;
    }
}
