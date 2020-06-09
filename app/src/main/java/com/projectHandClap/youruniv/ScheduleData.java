package com.projectHandClap.youruniv;

public class ScheduleData {
    public int schedule_id;
    public String schedule_class_string;
    public String schedule_title;
    public String schedule_memo;
    public String schedule_alarm;
    public String schedule_isDone;
    public long schedule_deadline;

    public ScheduleData() {
    }

    public ScheduleData(int schedule_id, String schedule_class_string, String schedule_title, String schedule_memo, String schedule_alarm, String schedule_isDone, long schedule_deadline) {
        this.schedule_id = schedule_id;
        this.schedule_class_string = schedule_class_string;
        this.schedule_title = schedule_title;
        this.schedule_memo = schedule_memo;
        this.schedule_alarm = schedule_alarm;
        this.schedule_isDone = schedule_isDone;
        this.schedule_deadline = schedule_deadline;
    }
}
