package com.cookandroid.mind_care;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;
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
        ImageButton prevMonthButton = findViewById(R.id.prevMonthButton);
        ImageButton nextMonthButton = findViewById(R.id.nextMonthButton);

        SleepDBHelper dbHelper = new SleepDBHelper(this);
        db = dbHelper.getReadableDatabase();

        currentMonthLabel.setText(currentMonth + "월");

        updateSleepGrid();

        prevMonthButton.setOnClickListener(v -> {
            if (currentMonth > 1) {
                currentMonth--;
                currentMonthLabel.setText(currentMonth + "월");
                updateSleepGrid();
            }
        });

        nextMonthButton.setOnClickListener(v -> {
            if (currentMonth < 12) {
                currentMonth++;
                currentMonthLabel.setText(currentMonth + "월");
                updateSleepGrid();
            }
        });
    }

    @SuppressLint("NewApi")
    private void updateSleepGrid() {
        sleepGrid.removeAllViews();

        Map<Integer, Integer> sleepDurationMap = getSleepDataForMonth(currentMonth);

        for (int i = 1; i <= 31; i++) {
            TextView daySquare = new TextView(this);
            daySquare.setText(String.valueOf(i));
            daySquare.setGravity(Gravity.CENTER);
            daySquare.setTextSize(16);
            daySquare.setLayoutParams(new GridLayout.LayoutParams());
            daySquare.setBackgroundColor(getColorForDuration(sleepDurationMap.getOrDefault(i, 0)));
            daySquare.setPadding(10, 10, 10, 10);
            sleepGrid.addView(daySquare);
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
        } else {
            return Color.parseColor("#CCE5CC"); // 가장 연한 색
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