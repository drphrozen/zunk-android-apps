package dk.znz.test;

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
import android.widget.ProgressBar;
import android.widget.TextView;

public class AccelerometerTester extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor		mSensor	= null;
	private Button mButton;
	private TextView	mTextView;
	private ProgressBar[] mProgressBars;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView = (TextView) findViewById(R.id.TextView01);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mProgressBars = new ProgressBar[] {
				(ProgressBar)findViewById(R.id.x),
				(ProgressBar)findViewById(R.id.y),
				(ProgressBar)findViewById(R.id.z)
		};
		for (ProgressBar progressBar : mProgressBars) {
			progressBar.setMax(10000);
		}
		
		mButton = (Button) findViewById(R.id.Button01);
		mButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(mSensor == null)
				{
					mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					mButton.setText("Stop");
					mTextView.setText(String.format("%s v%d [%s]\n%f\n%f\n%fmA", mSensor.getName(), mSensor.getVersion(), mSensor.getVendor(), mSensor.getMaximumRange(), mSensor.getResolution(), mSensor.getPower()));
					mSensorManager.registerListener(AccelerometerTester.this, mSensor, SensorManager.SENSOR_DELAY_GAME);
				}
				else
				{
					mSensor = null;
					mButton.setText("Start");
					mTextView.setText("Stopped");
					mSensorManager.unregisterListener(AccelerometerTester.this);
				}
			}
		});
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//mTextView.setText("Accuracy changed: " + accuracy);
	}

	public void onSensorChanged(SensorEvent event) {
		StringBuilder stringBuilder = new StringBuilder("Sensor changed: ");
		for (int i = 0; i < event.values.length - 1; i++) {
			;
			stringBuilder.append(Float.toString(event.values[i]));
			stringBuilder.append(',');
		}
		stringBuilder.append(Float.toString(event.values[event.values.length - 1]).toString());
		//mTextView.setText(stringBuilder.toString());
	}
}