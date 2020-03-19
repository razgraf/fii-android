package ro.vansoftware.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.util.ArrayMap;
import android.util.Log;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mPressure;
    private Sensor mProximity;

    private Map<Integer, String> list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setTitle("Sensors");

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s : sensors)
            list.put(s.getType(), "");


    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.e("Accuracy", accuracy + "");
    }

    public void onSensorChanged(SensorEvent event) {

        onDataBind();
        list.put(event.sensor.getType(), event.values[0]+"");


    }

    public void onDataBind(){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer,String> entry : list.entrySet())
        {

            result.append(mSensorManager.getDefaultSensor(entry.getKey()).getName());
            result.append(" - ");
            result.append(entry.getValue());
            result.append("\n");
        }

        ((TextView)findViewById(R.id.display)).setText(result);
    }

}
