package com.projectHandClap.youruniv;

public class MemoData {
    public long memo_id;
    public String memo_class_string;
    public String memo_title;
    public String memo_memo;
    public long memo_time;

    public MemoData() {
    }

    public MemoData(long memo_id, String memo_class_string, String memo_title, String memo_memo, long memo_time) {
        this.memo_id = memo_id;
        this.memo_class_string = memo_class_string;
        this.memo_title = memo_title;
        this.memo_memo = memo_memo;
        this.memo_time = memo_time;
    }
}
