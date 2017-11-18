package com.loscache.firebirdone.gui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.Toast;

import com.loscache.firebirdone.data.DataReaderDbContext;

/**
 * Created by cdsac on 05/11/2017.
 */

public class GesturesListener implements SensorEventListener {

    // Accelerometer
    private long lastRefresh = 0, lastMovement = 0;
    private float x = 0, y = 0, z = 0, lastX = 0, lastY = 0, lastZ = 0;

    // Giroscope
    private boolean antiClockWise = false, clockWise = false;

    // Proximity
    private float maxRange;

    public GesturesListener(float proximityMaxRange){
        maxRange = proximityMaxRange;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {

            float [] values =  event.values;

            switch(event.sensor.getType()) {

                case Sensor.TYPE_ACCELEROMETER:

                    long currentTime = event.timestamp;

                    x = values[0];
                    y = values[1];
                    z = values[2];

                    if(lastX == 0 && lastY == 0 && lastZ == 0) {
                        lastRefresh = currentTime;
                        lastMovement = currentTime;
                        lastX = x;
                        lastY = y;
                        lastZ = z;
                    }

                    long difference = currentTime - lastRefresh;

                    if(difference > 0) {

                        float movement = Math.abs((x + y + z) - (lastX - lastY - lastZ)) / difference;
                        int limit = 1500;
                        float minMovement = 1E-8f;

                        if(movement > minMovement) {

                            if(currentTime - lastMovement >= limit) {

                                if(Math.abs(y - lastY) < 1 && Math.abs(x - lastX) > 8 && Math.abs(z - lastZ) < 1) {

                                    // TO DO MOVEMENT
                                    Log.i("SENSOR", "ACCELEROMETER");

                                }
                            }
                        }

                        lastMovement = currentTime;
                    }

                    lastX = x;
                    lastY = y;
                    lastZ = z;
                    lastRefresh = currentTime;

                    break;

                case Sensor.TYPE_GYROSCOPE:

                    if(event.values[0] > 5f) { // anticlockwise
                        antiClockWise = !antiClockWise;
                    } else if(event.values[0] < - 5f) { // clockwise
                        clockWise = !clockWise;
                    }

                    if(antiClockWise && clockWise) {
                        // TO DO WHIP
                        Log.i("SENSOR", "GIROSCOPE");
                        antiClockWise = false;
                        clockWise = false;
                    }
                    break;

                case Sensor.TYPE_PROXIMITY:
                   if(event.values[0] < maxRange) {
                       Log.i("SENSOR", "PROXIMITY_NEAR");
                        // TO DO NEAR
                    } else {
                       Log.i("SENSOR", "PROXIMITY_AWAY");
                        // TO DO AWAY
                    }
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
