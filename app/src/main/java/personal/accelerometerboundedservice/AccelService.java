package personal.accelerometerboundedservice;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class AccelService extends Service implements SensorEventListener{

    double[] samples = new double[100];
    private int currentIndex = 0;
    private boolean isArrayFull = false;

    SensorManager sensorManager;
    Sensor accelerometer;

    MyBinder binder = new MyBinder();




    public AccelService() {

        Arrays.fill(samples,0.0);

    }
    @Override
    public void onCreate(){

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }


    public class MyBinder extends Binder {
        AccelService getService(){
            return AccelService.this;
        }
    }

    public String foo(){
        return "hello world";
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];

        synchronized (mutex) {
            samples[currentIndex % 100] = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
        }

    }


    private Object mutex = new Object();
    public double getRMS(){

        double total_sum = 0;

        synchronized (mutex) {
            for (double d : samples) {
                total_sum += d;
            }
        }
        return total_sum/100;

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}



