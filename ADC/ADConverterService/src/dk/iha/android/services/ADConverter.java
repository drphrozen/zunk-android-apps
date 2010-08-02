package dk.iha.android.services;

import java.io.FileNotFoundException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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
		} catch (FileNotFoundException e) {
			notifyUser();
			stopSelf();
		}
	}

	private void notifyUser() {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.pig_red, "SDCARD not found!", System.currentTimeMillis());
		Context context = getApplicationContext();
		CharSequence contentTitle = "IIOSS Logger";
		CharSequence contentText = "SDCARD not found!";
		Intent notificationIntent = new Intent(this, ADConverter.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		manager.notify(MISSING_SDCARD, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}