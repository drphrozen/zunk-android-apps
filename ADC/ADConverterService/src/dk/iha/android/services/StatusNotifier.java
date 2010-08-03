package dk.iha.android.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class StatusNotifier {

	private static NotificationManager getNotificationManager(Context context) {
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static void info(Context context, int id, CharSequence title, CharSequence message) {
		notifyUser(context, id, title, message, R.drawable.pig_green);
	}

	public static void error(Context context, int id, CharSequence title, CharSequence message) {
		notifyUser(context, id, title, message, R.drawable.pig_yellow);
	}

	public static void warning(Context context, int id, CharSequence title, CharSequence message) {
		notifyUser(context, id, title, message, R.drawable.pig_red);
	}

	private static void notifyUser(Context context, int id, CharSequence title, CharSequence message, int drawable_id) {
		
		Notification notification = new Notification(drawable_id, title, System.currentTimeMillis());
		Intent notificationIntent = new Intent(context, ADConverter.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, title, message, contentIntent);
		getNotificationManager(context).notify(id, notification);
	}
}
