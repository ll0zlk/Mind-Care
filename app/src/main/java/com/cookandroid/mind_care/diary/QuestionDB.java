package com.cookandroid.mind_care.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "mindcare.db";
    private static final int DB_VERSION = 1;

    public QuestionDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT NOT NULL, " +
                "isAnswered INTEGER DEFAULT 0, " +
                "date TEXT)");

        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 가장 기억에 남는 순간은 무엇이었나요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 하루 중 가장 감사했던 일은 무엇이었나요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 당신을 웃게 만든 일은 무엇이었나요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('최근 며칠 동안 성장했다고 느낀 순간은 무엇이었나요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 하루를 색으로 표현한다면 어떤 색인가요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 누구와 가장 많은 대화를 나누었나요? 어떤 대화였나요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('이루고 싶었던 목표 중 오늘 달성한 것은 무엇인가요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘의 나를 한 단어로 표현한다면 무엇인가요? 그 이유는요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('오늘 스스로를 칭찬해주고 싶은 이유는 무엇인가요?');");
        db.execSQL("INSERT INTO questions (question) VALUES ('내일을 위해 오늘 무엇을 준비하고 싶나요?');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }
}