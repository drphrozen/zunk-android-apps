package dk.iha.android.services;

import java.io.FileNotFoundException;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class ADConverter extends Service implements Handler.Callback {

	public static final String ADCONVERTER_SERVICE = "ADCONVERTER_SERVICE";

	public static final int SENSOR_STOPPED = 2;
	public static final int SENSOR_INFO = 1;
	public static final int SENSOR_RUNNING = 0;
	public static final int SENSOR_ERROR_EXCEPTION = -1;


	private Thread thread = null;

	private SensorRunnable runnable = null;
	
	private Handler handler;

	@Override
	/** Called when the activity is first created. */
	public void onCreate() {
		super.onCreate();

		handler = new Handler(this);
		
		try {
			runnable = new SensorRunnable();
			thread = new Thread(runnable);
			thread.start();
		} catch (FileNotFoundException e) {
			StatusNotifier.error(this, SENSOR_ERROR_EXCEPTION,
					"IIOSS Pig Logger", "SDCARD not found!");
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case SENSOR_ERROR_EXCEPTION:
			StatusNotifier.error(this, SENSOR_ERROR_EXCEPTION,
					"IIOSS Pig Logger", (CharSequence)msg.obj);
			break;
		case SENSOR_RUNNING:
			StatusNotifier.info(this, SENSOR_RUNNING, "IIOSS Pig Logger",
					"IIOSS Pig Logger is running.");
			break;
		case SENSOR_INFO: 
			StatusNotifier.info(this, SENSOR_RUNNING, "IIOSS Pig Logger",
					(CharSequence)msg.obj);
			break;
		default:
			return false;
		}
		return true;
	}
	
	public void sendMessage(int type, CharSequence message) {
		Message.obtain(handler, type, message).sendToTarget();
	}

	public void sendMessage(int type) {
		Message.obtain(handler, type).sendToTarget();
	}
}