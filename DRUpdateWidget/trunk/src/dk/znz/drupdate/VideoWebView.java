package dk.znz.drupdate;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class VideoWebView extends Activity {
  
  private WebView mWebView;
  private final String VIDEO_URI = "http://video-download.unwire.dk/DR/Update/101124/1290636902440_1867532_j249_p8.mp4";
  private final String HTML_CONTENT = "<!DOCTYPE HTML>\n<html>\n<body>\n<video width=\"320\" height=\"240\" controls onclick=\"this.play();\">\n<source src=\"%s\" type=\"video/mp4\">\nYour browser does not support html5 video tag!\n</video>\n</body>\n</html>";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.videowebview);
    
    mWebView = (WebView) findViewById(R.id.videowebview);
    mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; XXXXX like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/241 Safari/419.3");
    Uri uri = Uri.parse(getIntent().getExtras().getString("uri"));
    uri = Uri.parse(VIDEO_URI);
    mWebView.setBackgroundColor(Color.BLACK);
    String data = String.format(HTML_CONTENT, uri.toString());
    String base = String.format("%s://%s", uri.getScheme(), uri.getHost());
    Log.i("VideoWebView", data);
    Log.i("VideoWebView", uri.toString());
    Log.i("VideoWebView", base);
    mWebView.loadDataWithBaseURL(base, data, "text/html", "UTF-8", "about:blank");
  }
}
