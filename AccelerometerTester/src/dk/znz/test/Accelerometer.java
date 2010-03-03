package dk.znz.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer implements SensorEventListener {
	private Sensor mSensor;
	public Accelerometer(Context context) {
		SensorManager sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	
	public void onSensorChanged(SensorEvent event) {
	}
}
