package com.projectHandClap.youruniv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    //scheme 4 : picture
    private static final String PICTURE_TABLE_NAME = "picture";

    //scheme 5 : memo
    private static final String MEMO_TABLE_NAME = "memo";

    //scheme 6 : record
    private static final String RECORD_TABLE_NAME = "record";

    //scheme 7 : schedule
    private static final String SCHEDULE_TABLE_NAME = "schedule";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTING_CREATE_TABLE);
        db.execSQL(TIMETABLE_CREATE_TABLE);
        db.execSQL(CLASS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ SETTING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TIMETABLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CLASS_TABLE_NAME);
        onCreate(db);
    }

    public void restart(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ SETTING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TIMETABLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CLASS_TABLE_NAME);

        db.execSQL(SETTING_CREATE_TABLE);
        db.execSQL(TIMETABLE_CREATE_TABLE);
        db.execSQL(CLASS_CREATE_TABLE);
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

    public void updateClassData(String toStr){

    }

    public void deleteClassDataOne(String toStr){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM class WHERE class_string = '"+toStr+"'");
        db.close();
    }

    public void deleteClassData(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM class WHERE class_timetable_id = '"+id+"'");
        db.close();
    }
}
