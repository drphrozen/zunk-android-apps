package dk.znz.drupdate;

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

public class DRUpdateActivity extends Activity {
  private ListView    mList;
  private TextView    mUpdating;
  private LazyAdapter mLazyAdapter;
  private NewsClip[]  mNewsEntries;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mList = (ListView) findViewById(R.id.list);
    mUpdating = (TextView) findViewById(R.id.updating);

    new AsyncTask<Void, Float, NewsClip[]>() {

      private volatile Exception ex = null;

      // private ProgressDialog progressDialog;

      @Override
      protected void onPreExecute() {
        mUpdating.setVisibility(View.VISIBLE);
        // progressDialog = ProgressDialog.show(DRUpdateWidget.this,
        // "Indlæser nyheder", "Vent venligst..", true);
      };

      @Override
      protected NewsClip[] doInBackground(Void... params) {
        try {
          return new DRClipParser().parse().toArray(new NewsClip[0]);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return new NewsClip[0];
      }

      @Override
      protected void onProgressUpdate(Float... values) {
        // progressDialog.setMessage(String.format("Vent venligst.. %03.1f KiB",
        // values[0]/1024));
      };

      @Override
      protected void onPostExecute(NewsClip[] result) {
        mUpdating.setVisibility(View.GONE);
        // progressDialog.dismiss();
        mNewsEntries = result;
        mLazyAdapter = new LazyAdapter(DRUpdateActivity.this, mNewsEntries);
        mList.setAdapter(mLazyAdapter);
        if (ex != null) {
          Log.e(DRUpdateActivity.class.getName(), ex.getClass().getName() + ": " + ex.getMessage());
          AlertDialog.Builder builder = new AlertDialog.Builder(DRUpdateActivity.this);
          builder.setTitle("En fejl opstod, lukker!")
                 .setCancelable(false)
                 .setMessage(ex.getMessage())
                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                     DRUpdateActivity.this.finish();
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

  public final OnClickListener onCacheClearClickListener = new OnClickListener() {
                                                     public void onClick(View v) {
                                                       if (mLazyAdapter == null)
                                                         return;
                                                       mLazyAdapter.imageLoader.clearCache();
                                                       mLazyAdapter.notifyDataSetChanged();
                                                     }
                                                   };
}