package com.cookandroid.mind_care;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mind_care.diary.DiaryActivity;
import com.cookandroid.mind_care.diary.QuestionDiaryActivity;
import com.cookandroid.mind_care.sleep.SleepingActivity;

public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button diaryBtn = findViewById(R.id.diaryBtn);
        Button walkingBtn = findViewById(R.id.walkingBtn);
        Button myPageBtn = findViewById(R.id.myPageBtn);
        Button sleepingBtn = findViewById(R.id.sleepingBtn);

        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaryChoiceDialog();
            }
        });

        walkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.cookandroid.mind_care.WalkingActivity.class);
                startActivity(intent);
            }
        });

        myPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });

        sleepingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SleepingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDiaryChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("오늘은 어떤 일기를 쓰고 싶나요?");
        builder.setMessage("원하는 일기를 선택하여 오늘을 기록해보아요 :)");
        builder.setPositiveButton("자유 일기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("질문 일기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, QuestionDiaryActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}