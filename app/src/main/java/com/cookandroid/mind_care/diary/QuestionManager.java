package com.cookandroid.mind_care.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuestionManager {
    private final QuestionDB questionDb;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public QuestionManager(Context context) {
        questionDb = new QuestionDB(context);
    }

    private String getTodayDate() {
        return dateFormat.format(new Date());
    }

    public String getDailyQuestion() {
        SQLiteDatabase db = questionDb.getWritableDatabase();
        String today = getTodayDate();
        String question = null;

        db.execSQL("UPDATE Questions SET isAnswered = 0 WHERE date != ?", new String[]{today});
        Cursor cursor = db.rawQuery("SELECT id, question FROM Questions WHERE date = ? AND isAnswered = 0 LIMIT 1", new String[]{today});

        if (cursor.moveToFirst()) {
            question = cursor.getString(1);
        } else {
            Cursor newQuestionCursor = db.rawQuery("SELECT id, question FROM Questions WHERE isAnswered = 0 ORDER BY RANDOM() LIMIT 1", null);
            if (newQuestionCursor.moveToFirst()) {
                int questionId = newQuestionCursor.getInt(0);
                question = newQuestionCursor.getString(1);

                db.execSQL("UPDATE Questions SET date = ? WHERE id = ?", new Object[]{today, questionId});
            }
            newQuestionCursor.close();
        }
        cursor.close();
        db.close();
        return question;
    }

    public void markQuestionAnswered(String question) {
        SQLiteDatabase db = questionDb.getWritableDatabase();
        db.execSQL("UPDATE Questions SET isAnswered = 1 WHERE question = ?", new Object[]{question});
        db.close();
    }
}