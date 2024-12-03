package com.cookandroid.mind_care;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        ImageButton backButton = findViewById(R.id.backButton);
        Button diaryStatsButton = findViewById(R.id.diaryStatsButton);
        Button sleepStatsButton = findViewById(R.id.sleepStatsButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        diaryStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, DiaryStatsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sleepStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, SleepStatsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}