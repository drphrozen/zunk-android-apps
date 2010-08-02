package dk.iha.android.services;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;

public class ADConverter extends Activity {
	
	public static final String ADCONVERTER_SERVICE = "ADCONVERTER_SERVICE";
	
	// Error messages
	public static final int MISSING_SDCARD = 0;
	
	private final int sampleRate = 10; // samples pr second
	private Thread thread = null;
	
	private SensorRunnable runnable;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
					runnable = new SensorRunnable();
	        thread = new Thread(runnable);
	        thread.start();
				} catch (FileNotFoundException e) {
					NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					manager.notify(MISSING_SDCARD, new Notification(R.drawable.pig_red, "SDCARD not found!", System.currentTimeMillis()));
				}        
    }
}