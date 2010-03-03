package dk.au.pp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClientLogger extends Activity implements OnClickListener {

	static final int	PROGRESS_DIALOG	= 0;
	ProgressDialog		mProgressDialog;

	private Handler		mHandler				= new Handler() {
		public void handleMessage(Message msg) {
			String update = msg.getData().getString("update");
			mProgressDialog.setMessage(update);
		}
	};

	private Exercise1	mExercise1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button exercise1 = (Button) findViewById(R.id.exercise1);
		exercise1.setOnClickListener(this);
		Button exercise2 = (Button) findViewById(R.id.exercise2);
		exercise2.setOnClickListener(this);
		Button stop = (Button) findViewById(R.id.quit);
		stop.setOnClickListener(this);

		mExercise1 = new Exercise1(getApplicationContext(), mHandler);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case PROGRESS_DIALOG:
				try {
					mProgressDialog = new ProgressDialog(ClientLogger.this);
					mProgressDialog.setIndeterminate(true);
					mProgressDialog.setCancelable(true);
					mProgressDialog.setTitle("Running...");
					mProgressDialog.setMessage("Connecting");
					mProgressDialog.setOnDismissListener(new OnDismissListener() {
						public void onDismiss(DialogInterface dialog) {
							mExercise1.setState(Exercise1.STATE_DONE);
						}
					});
					EditText uri = (EditText) findViewById(R.id.EditText01);
					mExercise1.connect(new URI("http://" + uri.getText().toString()));
					mExercise1.start();
					return mProgressDialog;
				} catch (URISyntaxException e) {
					Toast.makeText(ClientLogger.this, "Please enter a valid host:port!", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				} catch (UnknownHostException e) {
					Toast.makeText(ClientLogger.this, e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(ClientLogger.this, e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

				return mProgressDialog;
			default:
				return null;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.exercise1:
				showDialog(PROGRESS_DIALOG);
				break;
			case R.id.exercise2:
				break;
			case R.id.quit:
				mHandler.removeCallbacks(mExercise1);
				mExercise1.disconnect();
				finish();
				break;
			default:
				break;
		}
	}
}