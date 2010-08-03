package dk.iha.android.services;

import java.io.FileNotFoundException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ADConverter extends Service {

	public static final String ADCONVERTER_SERVICE = "ADCONVERTER_SERVICE";

	// Error messages
	public static final int MISSING_SDCARD = 0;

	private final int sampleRate = 10; // samples pr second
	private Thread thread = null;

	private SensorRunnable runnable;

	@Override
	/** Called when the activity is first created. */
	public void onCreate() {
		super.onCreate();

		try {
			runnable = new SensorRunnable();
			thread = new Thread(runnable);
			thread.start();
			notifyUserRunning();
		} catch (FileNotFoundException e) {
			notifyUserSDError();
			stopSelf();
		}
	}

	private void notifyUserSDError() {
		
		StatusNotifier.error(this, MISSING_SDCARD, "IIOSS Pig Logger", "SDCARD not found!");
		
	}
	
	private void notifyUserRunning() {
		StatusNotifier.info(this, MISSING_SDCARD, "IIOSS Pig Logger", "IIOSS Pig Logger is running!");
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}