package dk.au.pp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class Exercise1 extends Thread implements LocationListener {

	public static final String	UPDATE_MESSAGE	= "update";

	private Context							mContext;
	private DataOutputStream		mStream					= null;
	private LocationManager			mLocationManager;
	private Handler							mHandler;

	private URI									mUri;

	final static int						STATE_DONE			= 0;
	final static int						STATE_RUNNING		= 1;
	private volatile int				mState;

	public Exercise1(Context context, Handler handler) {
		mContext = context;
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, this);

		mHandler = handler;

	}

	public void connect(URI uri) throws UnknownHostException, IOException {
		Socket socket = new Socket(uri.getHost(), uri.getPort());
		mStream = new DataOutputStream(socket.getOutputStream());
	}

	public void disconnect() {
		try {
			if (mStream != null)
				mStream.close();
		} catch (IOException e) {
			Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		mState = STATE_RUNNING;
		while (mState == STATE_RUNNING) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message message = mHandler.obtainMessage();
			Bundle bundle = new Bundle();
			Location l = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (l == null) {
				bundle.putString(UPDATE_MESSAGE, "Please enable GPS!");
				message.setData(bundle);
				mHandler.sendMessage(message);
			} else {
				try {
					mStream.writeDouble(l.getLongitude());
					mStream.writeDouble(l.getLatitude());
					mStream.writeFloat(l.getAccuracy());
					mStream.flush();
					bundle.putString(UPDATE_MESSAGE, String.format("%.2f, %.2f sent..", l.getLongitude(), l.getLatitude()));
					message.setData(bundle);
					mHandler.sendMessage(message);
				} catch (IOException e) {
					Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		}
	}

	public void onLocationChanged(Location location) {
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(mContext, "Provider disabled!", Toast.LENGTH_SHORT)
				.show();
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(mContext, "Provider enabled!", Toast.LENGTH_SHORT)
				.show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void setUri(URI uri) {
		this.mUri = uri;
	}

	public URI getUri() {
		return mUri;
	}

	public void setState(int state) {
		mState = state;
	}
}
