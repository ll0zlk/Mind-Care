package com.cookandroid.mind_care;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.DiaryDBHelper;

import java.util.HashMap;

public class DiaryStatsActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView diaryEntryTextView;
    private HashMap<String, String> diaryEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_stats);

        calendarView = findViewById(R.id.calendarView);
        diaryEntryTextView = findViewById(R.id.diaryEntryTextView);

        DiaryDBHelper dbHelper = new DiaryDBHelper(this);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            String diaryEntry = dbHelper.getDiaryEntry(selectedDate);

            if (diaryEntry != null) {
                diaryEntryTextView.setText(diaryEntry);
            } else {
                diaryEntryTextView.setText("일기를 작성하지 않은 날이에요.");
            }
        });
    }
}