package com.cookandroid.mind_care;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.diary.DiaryDBHelper;
import com.cookandroid.mind_care.diary.QuestionDiaryDBHelper;

public class DiaryStatsActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView diaryEntryTextView, diaryDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_stats);

        calendarView = findViewById(R.id.calendarView);
        diaryDateTextView = findViewById(R.id.diaryDateTextView);
        diaryEntryTextView = findViewById(R.id.diaryEntryTextView);

        DiaryDBHelper dbHelper = new DiaryDBHelper(this);
        QuestionDiaryDBHelper qdbHelper = new QuestionDiaryDBHelper(this);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            String diaryEntry = dbHelper.getDiaryEntry(selectedDate);
            String qdiaryEntry = qdbHelper.getDiaryEntry(selectedDate);
            String question = qdbHelper.getQuestion(selectedDate);
            diaryDateTextView.setText("📮 " + selectedDate);

            if (diaryEntry != null || qdiaryEntry != null) {
                if (diaryEntry != null) {
                    diaryEntryTextView.setText(diaryEntry);
                } else {
                    diaryEntryTextView.setText(question + "\n\n" + qdiaryEntry);
                }
            } else {
                diaryEntryTextView.setText("일기를 작성하지 않은 날이에요.");
            }
        });
    }
}