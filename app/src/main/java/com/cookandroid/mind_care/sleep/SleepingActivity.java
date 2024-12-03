package com.cookandroid.mind_care.sleep;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SleepingActivity extends AppCompatActivity {

    private EditText sleepDurationInput;
    private RadioGroup sleepQualityGroup;
    private Button saveButton;
    private SleepDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_page);

        sleepDurationInput = findViewById(R.id.sleepDurationInput);
        sleepQualityGroup = findViewById(R.id.sleepQualityGroup);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new SleepDBHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationText = sleepDurationInput.getText().toString();
                int selectedQualityId = sleepQualityGroup.getCheckedRadioButtonId();

                if (!durationText.isEmpty() && selectedQualityId != -1) {
                    int sleepDuration = Integer.parseInt(durationText);
                    String sleepQuality = ((RadioButton) findViewById(selectedQualityId)).getText().toString();
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    saveSleepRecord(currentDate, sleepDuration, sleepQuality);
                    Toast.makeText(SleepingActivity.this, "수면 기록이 저장되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SleepingActivity.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveSleepRecord(String date, int duration, String quality) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("duration", duration);
        values.put("quality", quality);

        db.insert("sleep_records", null, values);
    }
}