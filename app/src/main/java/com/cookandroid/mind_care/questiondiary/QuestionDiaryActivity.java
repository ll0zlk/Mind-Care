package com.cookandroid.mind_care.questiondiary;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.MainActivity;
import com.cookandroid.mind_care.R;

public class QuestionDiaryActivity extends AppCompatActivity {

    private Spinner yearSpinner, monthSpinner, daySpinner;
    private EditText diaryContentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_diary_page);

        ImageButton backButton = findViewById(R.id.backButton);
        Button saveDiaryBtn = findViewById(R.id.saveDiaryBtn);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        diaryContentInput = findViewById(R.id.diaryContentInput);

        setupSpinner(yearSpinner, R.array.year_array);
        setupSpinner(monthSpinner, R.array.month_array);
        setupSpinner(daySpinner, R.array.day_array);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionDiaryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedYear = yearSpinner.getSelectedItem().toString();
                String selectedMonth = monthSpinner.getSelectedItem().toString();
                String selectedDay = daySpinner.getSelectedItem().toString();
                String content = diaryContentInput.getText().toString();

                if (content.isEmpty()) {
                    Toast.makeText(QuestionDiaryActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                    Toast.makeText(QuestionDiaryActivity.this, selectedDate+ "의 일기가 기록되었습니다!", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    private void setupSpinner(Spinner spinner, int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResource, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}