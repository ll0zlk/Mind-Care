package com.cookandroid.mind_care;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.sleep.SleepDBHelper;

import java.util.HashMap;
import java.util.Map;

public class SleepStatsActivity extends AppCompatActivity {

    private TextView currentMonthLabel;
    private GridLayout sleepGrid;
    private int currentMonth = 12;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_stats);

        currentMonthLabel = findViewById(R.id.currentMonthLabel);
        sleepGrid = findViewById(R.id.sleepGrid);
        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        SleepDBHelper dbHelper = new SleepDBHelper(this);
        db = dbHelper.getReadableDatabase();

        currentMonthLabel.setText(currentMonth + "월");

        updateSleepGrid();

        prevMonthButton.setOnClickListener(v -> {
            currentMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
            currentMonthLabel.setText(currentMonth + "월");
            updateSleepGrid();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentMonth = (currentMonth == 12) ? 1 : currentMonth + 1;
            currentMonthLabel.setText(currentMonth + "월");
            updateSleepGrid();
        });
    }

    @SuppressLint("NewApi")
    private void updateSleepGrid() {
        Map<Integer, Integer> sleepDurationMap = getSleepDataForMonth(currentMonth);

        for (int i = 1; i <= 31; i++) {
            int resID = getResources().getIdentifier("view" + i, "id", getPackageName());
            TextView daySquare = findViewById(resID);

            int duration = sleepDurationMap.getOrDefault(i, 0);
            daySquare.setBackgroundColor(getColorForDuration(duration));
        }
    }

    private Map<Integer, Integer> getSleepDataForMonth(int month) {
        Map<Integer, Integer> sleepData = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT date, duration FROM sleep WHERE date LIKE ?",
                new String[]{"2024-" + (month < 10 ? "0" + month : month) + "%"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String fullDate = cursor.getString(cursor.getColumnIndex("date"));
                int day = Integer.parseInt(fullDate.split("-")[2]); // 날짜 추출
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                sleepData.put(day, duration);
            }
            cursor.close();
        }
        return sleepData;
    }

    private int getColorForDuration(int duration) {
        if (duration >= 14) {
            return Color.parseColor("#003300"); // 가장 어두운 색
        } else if (duration >= 10) {
            return Color.parseColor("#336633");
        } else if (duration >= 6) {
            return Color.parseColor("#66AA66");
        } else if (duration >= 3) {
            return Color.parseColor("#99CC99");
        } else if (duration >= 1) {
            return Color.parseColor("#CCE5CC");
        } else {
            return Color.parseColor("#C5C5C5");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}