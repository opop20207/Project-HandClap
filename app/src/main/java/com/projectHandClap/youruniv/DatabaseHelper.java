package com.projectHandClap.youruniv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projectHandClap.youruniv.Drawer.Gallery.GalleryActivity;

import java.sql.Time;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "YourUNIV.db";

    //scheme 1 : user setting
    private static final String SETTING_TABLE_NAME = "setting";
    private static final String SETTING_COLUMN_ID = "setting_id";
    private static final String SETTING_COLUMN_MAIN_TIMETABLE_ID = "setting_main_timetable_id";
    private static final String SETTING_COLUMN_DAY = "setting_day";
    private static final String SETTING_COLUMN_STIME = "setting_stime";
    private static final String SETTING_COLUMN_ETIME = "setting_etime";

    private static final String SETTING_CREATE_TABLE = "CREATE TABLE "
            + SETTING_TABLE_NAME + "("
            + SETTING_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SETTING_COLUMN_MAIN_TIMETABLE_ID + " INTEGER, "
            + SETTING_COLUMN_DAY + " TEXT, "
            + SETTING_COLUMN_STIME + " TEXT, "
            + SETTING_COLUMN_ETIME + " TEXT)";

    //scheme 2 : timetable
    private static final String TIMETABLE_TABLE_NAME = "timetable";
    private static final String TIMETABLE_COLUMN_TIMETABLE_ID = "timetable_id";
    private static final String TIMETABLE_COLUMN_TIMETABLE_TITLE = "timetable_title";

    private static final String TIMETABLE_CREATE_TABLE = "CREATE TABLE "
            + TIMETABLE_TABLE_NAME + "("
            + TIMETABLE_COLUMN_TIMETABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TIMETABLE_COLUMN_TIMETABLE_TITLE + " TEXT)";

    //scheme 3 : class
    private static final String CLASS_TABLE_NAME = "class";
    private static final String CLASS_COLUMN_CLASS_ID = "class_id";
    private static final String CLASS_COLUMN_CLASS_TIMETABLE_ID = "class_timetable_id";
    private static final String CLASS_COLUMN_CLASS_TITLE = "class_title";
    private static final String CLASS_COLUMN_CLASS_PLACE = "class_place";
    private static final String CLASS_COLUMN_CLASS_DAY = "class_day";
    private static final String CLASS_COLUMN_CLASS_STIME = "class_stime";
    private static final String CLASS_COLUMN_CLASS_ETIME = "class_etime";
    private static final String CLASS_COLUMN_CLASS_STRING = "class_string";
    private static final String CLASS_COLUMN_CLASS_ALARM = "class_alarm";
    private static final String CLASS_COLUMN_CLASS_PROFESSOR = "class_professor";
    private static final String CLASS_COLUMN_CLASS_COLOR = "class_color";
    private static final String CLASS_COLUMN_CLASS_MEMO = "class_memo";

    private static final String CLASS_CREATE_TABLE = "CREATE TABLE "
            + CLASS_TABLE_NAME + "("
            + CLASS_COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLASS_COLUMN_CLASS_TIMETABLE_ID + " INTEGER, "
            + CLASS_COLUMN_CLASS_TITLE + " TEXT, "
            + CLASS_COLUMN_CLASS_PLACE + " TEXT, "
            + CLASS_COLUMN_CLASS_DAY + " TEXT, "
            + CLASS_COLUMN_CLASS_STIME + " TEXT, "
            + CLASS_COLUMN_CLASS_ETIME + " TEXT, "
            + CLASS_COLUMN_CLASS_STRING + " TEXT, "
            + CLASS_COLUMN_CLASS_ALARM + " TEXT, "
            + CLASS_COLUMN_CLASS_PROFESSOR + " TEXT, "
            + CLASS_COLUMN_CLASS_COLOR + " TEXT, "
            + CLASS_COLUMN_CLASS_MEMO + " TEXT)";

    //scheme 4 : gallery
    private static final String GALLERY_TABLE_NAME = "gallery";
    private static final String GALLERY_COLUMN_GALLERY_ID = "gallery_id";
    private static final String GALLERY_COLUMN_GALLERY_CLASS_STRING = "gallery_class_string";
    private static final String GALLERY_COLUMN_GALLERY_IMAGE_PATH = "gallery_image_path";
    private static final String GALLERY_COLUMN_GALLERY_TITLE = "gallery_title";
    private static final String GALLERY_COLUMN_GALLERY_MEMO = "gallery_memo";
    private static final String GALLERY_COLUMN_GALLERY_TIME = "gallery_time";

    private static final String GALLERY_CREATE_TABLE = "CREATE TABLE "
            + GALLERY_TABLE_NAME + "("
            + GALLERY_COLUMN_GALLERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GALLERY_COLUMN_GALLERY_CLASS_STRING + " TEXT, "
            + GALLERY_COLUMN_GALLERY_IMAGE_PATH + " TEXT, "
            + GALLERY_COLUMN_GALLERY_TITLE + " TEXT, "
            + GALLERY_COLUMN_GALLERY_MEMO + " TEXT, "
            + GALLERY_COLUMN_GALLERY_TIME + " INTEGER)";

    //scheme 5 : memo
    private static final String MEMO_TABLE_NAME = "memo";
    private static final String MEMO_COLUMN_MEMO_ID = "memo_id";
    private static final String MEMO_COLUMN_MEMO_CLASS_STRING = "memo_class_string";
    private static final String MEMO_COLUMN_MEMO_TITLE = "memo_title";
    private static final String MEMO_COLUMN_MEMO_MEMO = "memo_memo";
    private static final String MEMO_COLUMN_MEMO_TIME = "memo_time";

    private static final String MEMO_CREATE_TABLE = "CREATE TABLE "
            + MEMO_TABLE_NAME + "("
            + MEMO_COLUMN_MEMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEMO_COLUMN_MEMO_CLASS_STRING + " TEXT, "
            + MEMO_COLUMN_MEMO_TITLE + " TEXT, "
            + MEMO_COLUMN_MEMO_MEMO + " TEXT, "
            + MEMO_COLUMN_MEMO_TIME + " INTEGER)";

    //scheme 6 : recorder
    private static final String RECORDER_TABLE_NAME = "recorder";

    //scheme 7 : schedule
    private static final String SCHEDULE_TABLE_NAME = "schedule";
    private static final String SCHEDULE_COLUMN_SCHEDULE_ID = "schedule_id";
    private static final String SCHEDULE_COLUMN_SCHEDULE_CLASS_STRING = "schedule_class_string";
    private static final String SCHEDULE_COLUMN_SCHEDULE_TITLE = "schedule_title";
    private static final String SCHEDULE_COLUMN_SCHEDULE_MEMO = "schedule_memo";
    private static final String SCHEDULE_COLUMN_SCHEDULE_ALARM = "schedule_alarm";
    private static final String SCHEDULE_COLUMN_SCHEDULE_ISDONE = "schedule_isdone";
    private static final String SCHEDULE_COLUMN_SCHEDULE_DEADLINE = "schedule_deadline";

    private static final String SCHEDULE_CREATE_TABLE = "CREATE TABLE "
            + SCHEDULE_TABLE_NAME + "("
            + SCHEDULE_COLUMN_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SCHEDULE_COLUMN_SCHEDULE_CLASS_STRING + " TEXT, "
            + SCHEDULE_COLUMN_SCHEDULE_TITLE + " TEXT, "
            + SCHEDULE_COLUMN_SCHEDULE_MEMO + " TEXT, "
            + SCHEDULE_COLUMN_SCHEDULE_ALARM + " TEXT, "
            + SCHEDULE_COLUMN_SCHEDULE_ISDONE + " TEXT, "
            + SCHEDULE_COLUMN_SCHEDULE_DEADLINE + " INTEGER)";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTING_CREATE_TABLE);
        db.execSQL(TIMETABLE_CREATE_TABLE);
        db.execSQL(CLASS_CREATE_TABLE);
        db.execSQL(GALLERY_CREATE_TABLE);
        db.execSQL(MEMO_CREATE_TABLE);

        db.execSQL(SCHEDULE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ SETTING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TIMETABLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CLASS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ GALLERY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MEMO_TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS "+ SCHEDULE_TABLE_NAME);
        onCreate(db);
    }

    public void restart(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ SETTING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TIMETABLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CLASS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ GALLERY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MEMO_TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS "+ SCHEDULE_TABLE_NAME);

        db.execSQL(SETTING_CREATE_TABLE);
        db.execSQL(TIMETABLE_CREATE_TABLE);
        db.execSQL(CLASS_CREATE_TABLE);
        db.execSQL(GALLERY_CREATE_TABLE);
        db.execSQL(MEMO_CREATE_TABLE);

        db.execSQL(SCHEDULE_CREATE_TABLE);
    }

    //CRUD Operation for scheme 1
    public void insertSetting(SettingData settingData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO setting(setting_main_timetable_id, setting_day, setting_stime, setting_etime) VALUES("
                +settingData.setting_main_timetable_id +", '"+settingData.setting_day+"', '"+settingData.setting_stime+"', '"+settingData.setting_etime+"');");
        db.close();
    }

    public SettingData getSetting(){
        SQLiteDatabase db = getReadableDatabase();
        SettingData ret = new SettingData();
        Cursor cursor = db.rawQuery("SELECT * FROM setting WHERE setting_id = 1", null);
        if(cursor.getCount()==0){
            cursor.close();
            db.close();
            return null;
        }
        while(cursor.moveToNext()){
            ret.setting_id = cursor.getInt(0);
            ret.setting_main_timetable_id = cursor.getInt(1);
            ret.setting_day = cursor.getString(2);
            ret.setting_stime = cursor.getString(3);
            ret.setting_etime = cursor.getString(4);
        }

        cursor.close();
        db.close();
        return ret;
    }

    public void updateSetting(SettingData settingData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE setting SET setting_main_timetable_id = "+settingData.setting_main_timetable_id
                +", setting_day = '"+settingData.setting_day+"', setting_stime = '"+settingData.setting_stime
                +"', setting_etime = '"+settingData.setting_etime+"' WHERE setting_id = 1");
        db.close();
    }

    //CRUD Operation for scheme 2
    public void insertTimetable(TimetableData timetableData){
        Log.e("!!!!!!","######");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO timetable(timetable_title) VALUES('"+timetableData.timetable_title+"');");
        db.close();
    }

    public ArrayList<TimetableData> getTimetable(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM timetable;", null);
        ArrayList<TimetableData> ret = new ArrayList<TimetableData>();
        while(cursor.moveToNext()){
            TimetableData temp = new TimetableData();
            temp.timetable_id = cursor.getInt(0);
            temp.timetable_title = cursor.getString(1);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public TimetableData getTimetableOne(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM timetable WHERE timetable_id = " + id, null);
        TimetableData temp = new TimetableData();
        while (cursor.moveToNext()) {
            temp.timetable_id = cursor.getInt(0);
            temp.timetable_title = cursor.getString(1);
        }

        cursor.close();
        db.close();
        return temp;
    }

    public void updateTimetable(TimetableData timetableData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE timetable SET timetable_title = '"+timetableData.timetable_title+"'"
                +" WHERE timetable_id = "+timetableData.timetable_id);
        db.close();
    }

    public void deleteTimetable(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM timetable WHERE timetable_id = '"+id+"'");
        db.close();
    }

    //CRUD Operation for scheme 3
    public void insertClassData(ClassData classData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO class(class_timetable_id, class_title, class_place, class_day, class_stime, class_etime, class_string, "
                + "class_alarm, class_professor, class_color, class_memo) VALUES("
                +classData.class_timetable_id+", '"+classData.class_title +"', '"+classData.class_place+"', '"+classData.class_day+"', '"
                +classData.class_stime+"', '"+classData.class_etime+"', '"+classData.class_string+"', '"+classData.class_alarm+"', '"+classData.class_professor+"', '"+classData.class_color+"', '"+classData.class_memo+"');");
        db.close();
    }

    public ArrayList<ClassData> getClassData(int timetable_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM class WHERE class_timetable_id = "+timetable_id+";", null);
        ArrayList<ClassData> ret = new ArrayList<ClassData>();
        while(cursor.moveToNext()){
            ClassData temp = new ClassData();
            temp.class_id = cursor.getLong(0);
            temp.class_timetable_id = cursor.getLong(1);
            temp.class_title = cursor.getString(2);
            temp.class_place = cursor.getString(3);
            temp.class_day = cursor.getString(4);
            temp.class_stime = cursor.getString(5);
            temp.class_etime = cursor.getString(6);
            temp.class_string = cursor.getString(7);
            temp.class_alarm = cursor.getString(8);
            temp.class_professor = cursor.getString(9);
            temp.class_color = cursor.getString(10);
            temp.class_memo = cursor.getString(11);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public ArrayList<ClassData> getClassDataByClassString(String class_string){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM class WHERE class_string = '"+class_string+"';", null);
        ArrayList<ClassData> ret = new ArrayList<ClassData>();
        while(cursor.moveToNext()){
            ClassData temp = new ClassData();
            temp.class_id = cursor.getLong(0);
            temp.class_timetable_id = cursor.getLong(1);
            temp.class_title = cursor.getString(2);
            temp.class_place = cursor.getString(3);
            temp.class_day = cursor.getString(4);
            temp.class_stime = cursor.getString(5);
            temp.class_etime = cursor.getString(6);
            temp.class_string = cursor.getString(7);
            temp.class_alarm = cursor.getString(8);
            temp.class_professor = cursor.getString(9);
            temp.class_color = cursor.getString(10);
            temp.class_memo = cursor.getString(11);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public ClassData getClassDataOneById(int class_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM class WHERE class_id = "+class_id+";", null);
        ClassData ret = new ClassData();
        while(cursor.moveToNext()){
            ClassData temp = new ClassData();
            temp.class_id = cursor.getLong(0);
            temp.class_timetable_id = cursor.getLong(1);
            temp.class_title = cursor.getString(2);
            temp.class_place = cursor.getString(3);
            temp.class_day = cursor.getString(4);
            temp.class_stime = cursor.getString(5);
            temp.class_etime = cursor.getString(6);
            temp.class_string = cursor.getString(7);
            temp.class_alarm = cursor.getString(8);
            temp.class_professor = cursor.getString(9);
            temp.class_color = cursor.getString(10);
            temp.class_memo = cursor.getString(11);
            ret = temp;
        }
        cursor.close();
        db.close();
        return ret;

    }

    public void updateClassData(ClassData classData){
        SQLiteDatabase db = getWritableDatabase();
        Log.e("fromUpdateClassData", classData.class_id+"!"+classData.class_memo);
        db.execSQL("UPDATE class SET " +
                "class_timetable_id = "+classData.class_timetable_id+", " +
                "class_title = '"+classData.class_title+"', " +
                "class_place = '"+classData.class_place+"', " +
                "class_day = '"+classData.class_day+"', " +
                "class_stime = '"+classData.class_stime+"', " +
                "class_etime = '"+classData.class_etime+"', " +
                "class_string = '"+classData.class_string+"', " +
                "class_alarm = '"+classData.class_alarm+"', " +
                "class_professor = '"+classData.class_professor+"', " +
                "class_color = '"+classData.class_color+"', " +
                "class_memo = '"+classData.class_memo+"' " +
                "WHERE class_id ="+classData.class_id+";");
        db.close();
    }

    public void deleteClassDataOneById(String toStr){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM class WHERE class_string = '"+toStr+"'");
        db.close();
    }

    public void deleteClassData(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM class WHERE class_timetable_id = '"+id+"'");
        db.close();
    }

    //CRUD Operation for scheme 4
    public void insertGallery(GalleryData galleryData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO gallery(gallery_class_string, gallery_image_path, gallery_title, "
                + "gallery_memo, gallery_time) VALUES("
                +galleryData.gallery_class_string+", '"+galleryData.gallery_image_path +"', '"+galleryData.gallery_title+"', '"
                +galleryData.gallery_memo+"', '"+galleryData.gallery_time+"');");
        db.close();
    }

    public ArrayList<GalleryData> getGallery(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gallery ORDER BY gallery_time;", null);
        ArrayList<GalleryData> ret = new ArrayList<>();
        while(cursor.moveToNext()){
            GalleryData temp = new GalleryData();
            temp.gallery_id = cursor.getInt(0);
            temp.gallery_class_string = cursor.getString(1);
            temp.gallery_image_path = cursor.getString(2);
            temp.gallery_title = cursor.getString(3);
            temp.gallery_memo = cursor.getString(4);
            temp.gallery_time = cursor.getLong(5);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public void updateGallery(GalleryData galleryData){

    }

    public void deleteGallery(GalleryData galleryData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM gallery WHERE gallery_id = "+galleryData.gallery_id);
        db.close();
    }

    public void deleteGalleryByClassString(String class_string){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM gallery WHERE gallery_string_id = '"+class_string+"'");
        db.close();
    }

    //CRUD Operation for scheme 5
    public void insertMemo(MemoData memoData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO memo(memo_class_string, memo_title, "
                + "memo_memo, memo_time) VALUES("
                +memoData.memo_class_string+", '"+memoData.memo_title+"', '"
                +memoData.memo_memo+"', '"+memoData.memo_time+"');");
        db.close();
    }

    public ArrayList<MemoData> getMemo(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memo ORDER BY memo_time;", null);
        ArrayList<MemoData> ret = new ArrayList<>();
        while(cursor.moveToNext()){
            MemoData temp = new MemoData();
            temp.memo_id = cursor.getLong(0);
            temp.memo_class_string = cursor.getString(1);
            temp.memo_title = cursor.getString(2);
            temp.memo_memo = cursor.getString(3);
            temp.memo_time = cursor.getLong(4);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public MemoData getMemoDataById(int memo_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memo WHERE memo_id = "+memo_id+";", null);
        MemoData ret = new MemoData();
        while(cursor.moveToNext()){
            ret.memo_id = cursor.getInt(0);
            ret.memo_class_string = cursor.getString(1);
            ret.memo_title = cursor.getString(2);
            ret.memo_memo = cursor.getString(3);
            ret.memo_time = cursor.getLong(4);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public void updateMemo(MemoData memoData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE memo SET " +
                "memo_class_string = "+memoData.memo_class_string+", " +
                "memo_title = '"+memoData.memo_title+"', " +
                "memo_memo = '"+ memoData.memo_memo+"', " +
                "memo_time = '"+memoData.memo_time+"' " +
                "WHERE memo_id ="+memoData.memo_id+";");
        db.close();
    }

    public void deleteMemo(MemoData memoData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM memo WHERE memo_id = "+memoData.memo_id);
        db.close();
    }

    //CRUD Operation for scheme 7
    public void insertSchedule(ScheduleData scheduleData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO schedule(schedule_class_string, schedule_title, schedule_memo, "
                + "schedule_alarm, schedule_isdone, schedule_deadline) VALUES("
                +scheduleData.schedule_class_string+", '"+scheduleData.schedule_title +"', '"+scheduleData.schedule_memo+"', '"
                +scheduleData.schedule_alarm+"', '"+scheduleData.schedule_isDone+"', '"+scheduleData.schedule_deadline+"');");
        db.close();
    }

    public ArrayList<ScheduleData> getSchedule(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM schedule ORDER BY schedule_deadline;", null);
        ArrayList<ScheduleData> ret = new ArrayList<ScheduleData>();
        while(cursor.moveToNext()){
            ScheduleData temp = new ScheduleData();
            temp.schedule_id = cursor.getInt(0);
            temp.schedule_class_string = cursor.getString(1);
            temp.schedule_title = cursor.getString(2);
            temp.schedule_memo = cursor.getString(3);
            temp.schedule_alarm = cursor.getString(4);
            temp.schedule_isDone = cursor.getString(5);
            temp.schedule_deadline = cursor.getLong(6);
            ret.add(temp);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public void updateSchedule(ScheduleData scheduleData){

    }

    public void deleteSchedule(ScheduleData scheduleData){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM schedule WHERE schedule_id = "+scheduleData.schedule_id);
        db.close();
    }

    public void deleteScheduleByClassString(String class_string){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM schedule WHERE schedule_class_string = '"+class_string+"'");
        db.close();
    }
}
