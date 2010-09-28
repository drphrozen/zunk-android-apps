package dk.znz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fedorvlasov.lazylist.LazyAdapter;

public class DRUpdateWidget extends Activity {
	private ListView mListView;
	private LazyAdapter mLazyAdapter;
	private NewsEntry[] mNewsEntries;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mListView = (ListView) findViewById(R.id.list);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(listener);

		new AsyncTask<Void, Float, NewsEntry[]>() {

			private volatile Exception ex = null;
			private ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(DRUpdateWidget.this, "Indlæser nyheder", "Vent venligst..", true);
			};
			
			@Override
			protected NewsEntry[] doInBackground(Void... params) {
				try {
					return DRUpdate.getNewsEntries(new ProgressInfo() {
						public void progressChanged(long progress) {
							publishProgress((float)progress);
						}
					});
				} catch (Exception e) {
					ex = e;
					return new NewsEntry[0];
				}
			}

			@Override
			protected void onProgressUpdate(Float... values) {
				progressDialog.setMessage(String.format("Vent venligst.. %03.1f KiB", values[0]/1024));
			};
			
			@Override
			protected void onPostExecute(NewsEntry[] result) {
				progressDialog.dismiss();
				mNewsEntries = result;
				mLazyAdapter = new LazyAdapter(DRUpdateWidget.this, mNewsEntries);
				mListView.setAdapter(mLazyAdapter);
				if(ex != null) {
					Log.e(DRUpdateWidget.class.getName(), ex.getClass().getName() + ": " + ex.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(DRUpdateWidget.this);
					builder.setTitle("En fejl opstod, lukker!")
					       .setCancelable(false)
					       .setMessage(ex.getMessage())
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   DRUpdateWidget.this.finish();
					           }
					       });
					builder.create().show();
				}
			}
		}.execute();
	}

	@Override
	public void onDestroy() {
		if (mLazyAdapter != null)
			mLazyAdapter.imageLoader.stopThread();
		mListView.setAdapter(null);
		super.onDestroy();
	}

	public OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			if(mLazyAdapter == null) return;
			mLazyAdapter.imageLoader.clearCache();
			mLazyAdapter.notifyDataSetChanged();
		}
	};
}