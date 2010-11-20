package dk.znz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.LazyAdapter;

public class DRUpdateWidget extends Activity {
	private ListView mList;
	private TextView mUpdating;
	private LazyAdapter mLazyAdapter;
	private NewsEntry[] mNewsEntries;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mList = (ListView) findViewById(R.id.list);
		mUpdating = (TextView) findViewById(R.id.updating);

		new AsyncTask<Void, Float, NewsEntry[]>() {

			private volatile Exception ex = null;
//			private ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				mUpdating.setVisibility(View.VISIBLE);
//				progressDialog = ProgressDialog.show(DRUpdateWidget.this, "Indlæser nyheder", "Vent venligst..", true);
			};
			
			@Override
			protected NewsEntry[] doInBackground(Void... params) {
				try {
					return DRUpdate.getNewsEntries();
				} catch (Exception e) {
					ex = e;
					return new NewsEntry[0];
				}
			}

			@Override
			protected void onProgressUpdate(Float... values) {
//				progressDialog.setMessage(String.format("Vent venligst.. %03.1f KiB", values[0]/1024));
			};
			
			@Override
			protected void onPostExecute(NewsEntry[] result) {
				mUpdating.setVisibility(View.GONE);
//				progressDialog.dismiss();
				mNewsEntries = result;
				mLazyAdapter = new LazyAdapter(DRUpdateWidget.this, mNewsEntries);
				mList.setAdapter(mLazyAdapter);
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
		mList.setAdapter(null);
		super.onDestroy();
	}

	public OnClickListener onCacheClearClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(mLazyAdapter == null) return;
			mLazyAdapter.imageLoader.clearCache();
			mLazyAdapter.notifyDataSetChanged();
		}
	};
}