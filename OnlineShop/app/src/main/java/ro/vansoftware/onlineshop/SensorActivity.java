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

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gravity;
    private Sensor pressure;
    private Sensor proximity;

    private Map<Integer, String> list = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setTitle("Sensors");

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s : sensors)
            list.put(s.getType(), "");


    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

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

            result.append(sensorManager.getDefaultSensor(entry.getKey()).getName());
            result.append(" - ");
            result.append(entry.getValue());
            result.append("\n");
        }

        ((TextView)findViewById(R.id.display)).setText(result);
    }

}
