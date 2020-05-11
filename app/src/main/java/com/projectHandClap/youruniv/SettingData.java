package com.projectHandClap.youruniv;

public class SettingData {
    int setting_id;
    int setting_main_timetable_id;
    String setting_day;
    String setting_time;

    public SettingData() {
        setting_id = -1;
    }

    public SettingData(int setting_id, int setting_main_timetable_id, String setting_day, String setting_time) {
        this.setting_id = setting_id;
        this.setting_main_timetable_id = setting_main_timetable_id;
        this.setting_day = setting_day;
        this.setting_time = setting_time;
    }
}