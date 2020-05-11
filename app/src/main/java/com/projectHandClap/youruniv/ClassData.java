package com.projectHandClap.youruniv;

public class ClassData {
    long class_id;
    String class_title;
    int class_day;
    int class_stime;
    int class_etime;

    public ClassData(long class_id, String class_title, int class_day, int class_stime, int class_etime) {
        this.class_id = class_id;
        this.class_title = class_title;
        this.class_day = class_day;
        this.class_stime = class_stime;
        this.class_etime = class_etime;
    }
}
