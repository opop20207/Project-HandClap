package com.projectHandClap.youruniv;

public class RecordData {
    public int record_id;
    public String record_class_string;
    public String record_file_path;
    public String record_title;
    public long record_time;

    public RecordData() {
    }

    public RecordData(int record_id, String record_class_string, String record_file_path, String record_title, long record_time) {
        this.record_id = record_id;
        this.record_class_string = record_class_string;
        this.record_file_path = record_file_path;
        this.record_title = record_title;
        this.record_time = record_time;
    }
}
