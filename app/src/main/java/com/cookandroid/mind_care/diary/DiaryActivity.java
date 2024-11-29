package com.cookandroid.mind_care.diary;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.MainActivity;
import com.cookandroid.mind_care.R;

public class DiaryActivity extends AppCompatActivity {

    private Spinner yearSpinner, monthSpinner, daySpinner;
    private EditText diaryContentInput;
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private ImageView selectedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_page);

        ImageButton backButton = findViewById(R.id.backButton);
        Button addPhotoBtn = findViewById(R.id.addPhotoBtn);
        Button saveDiaryBtn = findViewById(R.id.saveDiaryBtn);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        diaryContentInput = findViewById(R.id.diaryContentInput);
        selectedImageView = findViewById(R.id.selectedImageView);

        DiaryDBHelper dbHelper = new DiaryDBHelper(this);

        setupSpinner(yearSpinner, R.array.year_array);
        setupSpinner(monthSpinner, R.array.month_array);
        setupSpinner(daySpinner, R.array.day_array);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
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
                    Toast.makeText(DiaryActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedDate = selectedYear + "-" + String.format("%02d", Integer.parseInt(selectedMonth)) + "-" + String.format("%02d", Integer.parseInt(selectedDay));

                    dbHelper.insertDiaryEntry(selectedDate, content);
                    Toast.makeText(DiaryActivity.this, selectedDate + "의 일기가 기록되었습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupSpinner(Spinner spinner, int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResource, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                selectedImageView.setImageURI(selectedImageUri);
                Toast.makeText(this, "사진이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}