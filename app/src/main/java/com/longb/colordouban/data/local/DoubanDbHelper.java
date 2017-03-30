package com.longb.colordouban.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DoubanDbHelper extends SQLiteOpenHelper {
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    /**
     * 数据库名
     */
    public static final String DB_NAME = "DouBan.db";

    /**
     * 选择城市历史记录数据表
     */
    public static final String TABLE_CITY_HISTORY = "tb_city_history";


    public DoubanDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CITY_HISTORY + " ("
                + "id INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "parent INTEGER,"
                + "py TEXT,"
                + "pys TEXT,"
                + "type INTEGER,"
                + "select_time Long)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
