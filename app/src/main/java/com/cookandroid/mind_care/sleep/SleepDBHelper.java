package com.cookandroid.mind_care.sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sleep_records.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "sleep";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_QUALITY = "quality";

    public SleepDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DATE + " TEXT PRIMARY KEY, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_QUALITY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertSleepEntry(String date, String duration, String quality) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_QUALITY, quality);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public String getSleepEntry(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DURATION},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String content = cursor.getString(0);
            cursor.close();
            return content;
        }
        return null;
    }
}