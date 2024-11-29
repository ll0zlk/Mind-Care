package com.cookandroid.mind_care.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionDiaryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qdiary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "diary";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CONTENT = "content";

    public QuestionDiaryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DATE + " TEXT PRIMARY KEY, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_CONTENT + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertDiaryEntry(String date, String question, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_CONTENT, content);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public String getDiaryEntry(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_CONTENT},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String content = cursor.getString(0);
            cursor.close();
            return content;
        }
        return null;
    }

    public String getQuestion(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_QUESTION},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String question = cursor.getString(0);
            cursor.close();
            return question;
        }
        return null;
    }
}