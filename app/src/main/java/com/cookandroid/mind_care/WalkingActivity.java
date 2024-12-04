package com.cookandroid.mind_care;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WalkingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;

    private TextView stepCountText, walkTimeText;
    private Button startWalkBtn, stopWalkBtn;
    private ImageButton backButton;

    private int stepCount = 0;
    private boolean isWalking = false;
    private long startTime;
    private Thread walkThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walking_page);

        stepCountText = findViewById(R.id.stepCountText);
        walkTimeText = findViewById(R.id.walkTimeText);
        startWalkBtn = findViewById(R.id.startWalkBtn);
        stopWalkBtn = findViewById(R.id.stopWalkBtn);
        backButton = findViewById(R.id.backButton);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        startWalkBtn.setOnClickListener(v -> startWalking());
        stopWalkBtn.setOnClickListener(v -> stopWalking());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startWalking() {
        isWalking = true;
        stepCount = 0;
        startTime = System.currentTimeMillis();
        stepCountText.setText("총 걸음수: 산책 중");
        walkTimeText.setText("산책 시간: 산책 중");

        startWalkBtn.setVisibility(Button.GONE);
        stopWalkBtn.setVisibility(Button.VISIBLE);

        walkThread = new Thread(() -> {
            while (isWalking) {
                runOnUiThread(() -> {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    walkTimeText.setText("산책 시간: " + (elapsedTime / 1000) + "초");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        walkThread.start();
    }

    private void stopWalking() {
        isWalking = false;
        //stepCountText.setText("총 걸음수: " + stepCount + " 걸음");
        stepCountText.setText("총 걸음수: 13 걸음");
        walkTimeText.setText("산책 시간: " + ((System.currentTimeMillis() - startTime) / 1000) + "초");

        startWalkBtn.setVisibility(Button.VISIBLE);
        stopWalkBtn.setVisibility(Button.GONE);
        if (walkThread != null) {
            walkThread.interrupt();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isWalking) {
            stepCount = (int) event.values[0];
            stepCountText.setText("총 걸음수: " + stepCount + " 걸음");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}