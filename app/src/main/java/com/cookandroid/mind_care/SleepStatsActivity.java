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

    public class SleepData {
        private int duration;  // 수면 지속 시간
        private String quality; // 수면 질

        public SleepData(int duration, String quality) {
            this.duration = duration;
            this.quality = quality;
        }

        public int getDuration() {
            return duration;
        }

        public String getQuality() {
            return quality;
        }
    }


    @SuppressLint("NewApi")
    private void updateSleepGrid() {
        Map<Integer, SleepData> sleepDataMap = getSleepDataForMonth(currentMonth);

        for (int i = 1; i <= 31; i++) {
            int resID = getResources().getIdentifier("view" + i, "id", getPackageName());
            TextView daySquare = findViewById(resID);

            SleepData sleepData = sleepDataMap.getOrDefault(i, new SleepData(0, "unknown"));
            int duration = sleepData.getDuration();
            String quality = sleepData.getQuality();

            daySquare.setBackgroundColor(getColorForDuration(duration));
            daySquare.setTextColor(getColorForQuality(quality));
        }
    }

    private Map<Integer, SleepData> getSleepDataForMonth(int month) {
        Map<Integer, SleepData> sleepDataMap = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT date, duration, quality FROM sleep WHERE date LIKE ?",
                new String[]{"2024-" + (month < 10 ? "0" + month : month) + "%"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String fullDate = cursor.getString(cursor.getColumnIndex("date"));
                int day = Integer.parseInt(fullDate.split("-")[2]);
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                String quality = cursor.getString(cursor.getColumnIndex("quality"));

                sleepDataMap.put(day, new SleepData(duration, quality)); // SleepData 객체 생성
            }
            cursor.close();
        }
        return sleepDataMap;
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

    private int getColorForQuality(String quality) {
        switch (quality) {
            case "불량":
                return Color.parseColor("#ff9595");
            case "보통":
                return Color.parseColor("#FFE08C");
            case "좋음":
                return Color.parseColor("#B2CCFF");
            default:
                return Color.parseColor("#7AFFFFFF"); // 기본 색상
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