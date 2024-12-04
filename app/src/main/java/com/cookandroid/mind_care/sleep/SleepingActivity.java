package com.cookandroid.mind_care.sleep;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.R;

public class SleepingActivity extends AppCompatActivity {
    private SleepDBHelper dbHelper;
    private EditText sleepDurationInput;
    private RadioGroup sleepQualityGroup;
    private Spinner yearSpinner, monthSpinner, daySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_page);

        dbHelper = new SleepDBHelper(this);
        sleepDurationInput = findViewById(R.id.sleepDurationInput);
        sleepQualityGroup = findViewById(R.id.sleepQualityGroup);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSleepRecord();
            }
        });
    }

    private void saveSleepRecord() {
        String date = getSelectedDate();
        String durationStr = sleepDurationInput.getText().toString();
        int duration;

        if (durationStr.isEmpty()) {
            Toast.makeText(this, "수면 시간을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        duration = Integer.parseInt(durationStr);
        String quality = getSelectedQuality();

        dbHelper.insertSleepEntry(date, duration, quality);
        Toast.makeText(this, "수면 기록이 저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    private String getSelectedDate() {
        String year = yearSpinner.getSelectedItem().toString();
        String month = monthSpinner.getSelectedItem().toString();
        String day = daySpinner.getSelectedItem().toString();

        return year + "-" + (month.length() < 2 ? "0" + month : month) + "-" + (day.length() < 2 ? "0" + day : day);
    }

    private String getSelectedQuality() {
        int selectedId = sleepQualityGroup.getCheckedRadioButtonId();
        RadioButton selectedQualityButton = findViewById(selectedId);
        return selectedQualityButton != null ? selectedQualityButton.getText().toString() : "Unknown";
    }
}