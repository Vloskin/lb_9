package com.example.lb_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    Button backButton;
    TextView textView;
    SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        backButton = (Button) findViewById(R.id.backButton);
        textView = (TextView) findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(sensor!= null)
        {
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
        }
        else
        {
            textView.setText("Сенсор не обнаружен");
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LightActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        textView.setText("Освещённость  :"+  event.values[0]+ "lux");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}