package com.example.lb_9;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView orientationTextView;
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orientationTextView = findViewById(R.id.orientationTextView);

        // Получаем экземпляр SensorManager для работы с сенсорами
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Получаем экземпляр Rotation Vector Sensor
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Регистрируем SensorEventListener
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Отменяем регистрацию SensorEventListener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // Получаем значения вектора поворота
            float[] rotationMatrix = new float[9];
            float[] rotationVector = new float[3];
            System.arraycopy(event.values, 0, rotationVector, 0, 3);
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

            // Получаем ориентацию в радианах
            float[] orientation = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);

            // Преобразуем радианы в градусы и округляем до десятых
            float azimuth = (float) Math.toDegrees(orientation[0]);
            float pitch = (float) Math.toDegrees(orientation[1]);
            float roll = (float) Math.toDegrees(orientation[2]);

            // Отображаем значения ориентации
            orientationTextView.setText("Orientation: Azimuth - " + String.format("%.1f", azimuth)
                    + ", Pitch - " + String.format("%.1f", pitch)
                    + ", Roll - " + String.format("%.1f", roll));
        }
    }
}