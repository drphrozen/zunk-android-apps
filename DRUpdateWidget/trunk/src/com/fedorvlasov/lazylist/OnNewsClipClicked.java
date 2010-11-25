package com.fedorvlasov.lazylist;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import dk.znz.drupdate.NewsClip;

public class OnNewsClipClicked implements OnClickListener {

  private final Activity mActivity;
  private final NewsClip mClip;
  
  public OnNewsClipClicked(Activity activity, NewsClip clip) {
    super();
    mActivity = activity;
    mClip = clip;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    try {
      intent.setDataAndType(Uri.parse(IPhoneFaker.load(mClip.getVideoLocation())), "video/*");
      mActivity.startActivity(intent);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static class IPhoneFaker {
    
    public static String load(String url) throws MalformedURLException, IOException {
      String lastURL = url;
      Log.i("IPhoneFaker", url);
      while((url = _load(url)) != null) {
        Log.i("IPhoneFaker", url);
        lastURL = url;
      }
      return lastURL;
    }
    
    private static String _load(String url) throws MalformedURLException, IOException {
      HttpURLConnection connection = (HttpURLConnection)(new URL(url).openConnection());
      connection.setRequestProperty("user-agent", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
      connection.setInstanceFollowRedirects(false);
      Map<String, List<String>> map = connection.getHeaderFields();
      List<String> list = map.get("location");
      return list != null ? list.get(0) : null;
    }
  }
}
