package com.adamaszhu.androidcourse.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoButton;

public class SensorDemo extends Demo {

    @Override
    public int getTitleId() {
        return R.string.feature_sensor;
    }

    private SensorEventListener listener;
    private SensorManager sensorManager;

    @Override
    public void initialize() {
        sensorManager = activity.getSystemService(SensorManager.class);
        listener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    float pressure = sensorEvent.values[0];
                    getListener().updateOutput(String.valueOf(pressure));
                } else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float pressure = sensorEvent.values[0];
                    getListener().updateOutput(String.valueOf(pressure));
                }
            }
        };
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                observeAcc(),
                ignoreAcc(),
                observePressure(),
                ignorePressure()
        };
    }

    private DemoButton observeAcc() {
        return new DemoButton(R.string.feature_sensor_observe_acc, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        });
    }

    private DemoButton ignoreAcc() {
        return new DemoButton(R.string.feature_sensor_ignore_acc, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(listener);
            }
        });
    }

    private DemoButton observePressure() {
        return new DemoButton(R.string.feature_sensor_observe_pressure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        });
    }

    private DemoButton ignorePressure() {
        return new DemoButton(R.string.feature_sensor_ignore_pressure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(listener);
            }
        });
    }
}
