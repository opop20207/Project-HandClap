package com.projectHandClap.youruniv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String CLASS_COLUMN_ID = "class_id";
    private static final String CLASS_COLUMN_TIMETABLE_ID = "class_timetable_id";
    private static final String CLASS_COLUMN_TITLE = "class_title";
    private static final String CLASS_COLUMN_DAY = "class_day";
    private static final String CLASS_COLUMN_STIME = "class_stime";
    private static final String CLASS_COLUMN_ETIME = "class_etime";

    private static final String CLASS_CREATE_TABLE = "CREATE TABLE "
            + CLASS_TABLE_NAME + "("
            + CLASS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLASS_COLUMN_TIMETABLE_ID + " INTEGER, "
            + CLASS_COLUMN_TITLE + " TEXT, "
            + CLASS_COLUMN_DAY + " INTEGER, "
            + CLASS_COLUMN_STIME + " INTEGER, "
            + CLASS_COLUMN_ETIME + " INTEGER)";

    //scheme 4 : picture
    private static final String PICTURE_TABLE_NAME = "picture";

    //scheme 5 : memo
    private static final String MEMO_TABLE_NAME = "memo";

    //scheme 6 : record
    private static final String RECORD_TABLE_NAME = "record";

    //scheme 7 : schedule
    private static final String SCEDULE_TABLE_NAME = "schedule";

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

    //CRUD Operation for scheme 1
    public void insertSetting(int id, String day, String stime, String etime){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO setting VALUES('"+id
                +"', '"+day+"', '"+stime+"', '"+etime+"');");
        db.close();
    }

    public void updateSetting(int id, String day, String stime, String etime){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE setting SET setting_main_timetable_id = "+id
                +", setting_day = "+day+", setting_stime = "+stime
                +", setting_etime = "+etime+" WHERE setting_id = 1");
        db.close();
    }

    public SettingData getSetting(){
        SQLiteDatabase db = getReadableDatabase();
        SettingData ret = new SettingData();
        Cursor cursor = db.rawQuery("SELECT * FROM SETTING", null);
        while(cursor.moveToNext()){
            ret.setting_id = cursor.getInt(0);
            ret.setting_main_timetable_id = cursor.getInt(1);
            ret.setting_day = cursor.getString(2);
            ret.setting_time = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return ret;
    }

    //CRUD Operation for scheme 2
    public void insertTimetable(String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO setting VALUES('"+title+");");
        db.close();
    }

    public void updateTimetable(int id, int title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE timetable SET timetable_title = "+title
                +" WHERE timetable_id = "+id);
        db.close();
    }

    public void getTimetable(){

    }

    //CRUD Operation for scheme 3
}
