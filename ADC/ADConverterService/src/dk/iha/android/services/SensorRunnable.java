package dk.iha.android.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dk.iha.iioss.Log;

public class SensorRunnable implements Runnable {

	private volatile boolean running;
	
	private final ADConverter adConverter;
	
	/**
	 * Raw data from the sensor may be convert to kilogram using this factor.
	 */
	private final float weightFactor = 0.5f;

	public SensorRunnable(ADConverter adConverter) throws FileNotFoundException {
		output = new FileOutputStream("/sdcard/IIOSS" + System.currentTimeMillis() / 1000);
		running = false;
		this.adConverter = adConverter;
	}

	final FileOutputStream output;

	public void run() {
		final Sensor adcSensor;
		final SampleFilter filter;
		try {
			adcSensor = new Sensor(Sensor.ADC_PROVIDER, 0);
			filter = new SampleFilter(2, 10);
			while (running) {
				adConverter.sendMessage(ADConverter.SENSOR_RUNNING);
				try {
					int value = adcSensor.readInteger();
					if (filter.put(value) == false || value <= 2)
						continue;
					handleWeightTrigger(value);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			adConverter.sendMessage(ADConverter.SENSOR_STOPPED);
		} catch (Exception e) {
			adConverter.sendMessage(ADConverter.SENSOR_ERROR_EXCEPTION, e.getMessage());
			e.printStackTrace();
		}
		
	}

	private void handleWeightTrigger(int value) {
		Log.Entry.newBuilder().setTimestamp(System.currentTimeMillis()).setWeight(value * weightFactor);
	}

	private void checkRFID() {

	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
