package dk.znz.test;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AccelerometerTester extends Activity implements
		SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mSensor = null;
	private Button mButton;
	private TextView mTextView;
	private DataOutputStream mOutput;
	private float[] mLastValues = { 0f, 0f, 0f };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView = (TextView) findViewById(R.id.TextView01);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mButton = (Button) findViewById(R.id.Button01);
		mButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mSensor == null) {
					try {
						mOutput = new DataOutputStream(new FileOutputStream(
								"/sdcard/accelerometer.dat"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						mOutput = null;
					}
					mSensor = mSensorManager
							.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					mButton.setText("Stop");
					mTextView.setText(String.format(
							"%s v%d [%s]\n%f\n%f\n%fmA", mSensor.getName(),
							mSensor.getVersion(), mSensor.getVendor(), mSensor
									.getMaximumRange(),
							mSensor.getResolution(), mSensor.getPower()));
					mSensorManager.registerListener(AccelerometerTester.this,
							mSensor, SensorManager.SENSOR_DELAY_GAME);
				} else {
					mSensorManager.unregisterListener(AccelerometerTester.this);
					if (mOutput != null)
						try {
							mOutput.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					mSensor = null;
					mButton.setText("Start");
					mTextView.setText("Stopped");
				}
			}
		});
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// mTextView.setText("Accuracy changed: " + accuracy);
	}

	public void onSensorChanged(SensorEvent event) {
		if (mOutput != null) {
			try {
				for (int i = 0; i < 3; i++) {
					mOutput.writeFloat(event.values[i]);
					mOutput.writeFloat(event.values[i] - mLastValues[i]);
					mLastValues[i] = event.values[i];
				}
				mOutput.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}