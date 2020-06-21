package com.projectHandClap.youruniv;

public class SettingData {
    public int setting_id;
    public int setting_main_timetable_id;
    public String setting_day;
    public String setting_stime;
    public String setting_etime;

    public SettingData() {
        setting_id = -1;
    }

    public SettingData(int setting_main_timetable_id, String setting_day, String setting_stime, String setting_etime) {
        this.setting_main_timetable_id = setting_main_timetable_id;
        this.setting_day = setting_day;
        this.setting_stime = setting_stime;
        this.setting_etime = setting_etime;
    }
}