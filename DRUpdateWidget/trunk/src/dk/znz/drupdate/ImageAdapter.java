package dk.znz.drupdate;

import java.net.URL;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
  private Context          mContext;

  private final Drawable[] mDrawables;
  private final NewsClip[] mNewsEntries;
  private final Drawable   mStub;

  public ImageAdapter(Context c, NewsClipStore store) {
    mContext = c;
    mStub = mContext.getResources().getDrawable(R.drawable.stub);
    NewsClip[] tmp = store.getClips();
    mNewsEntries = (tmp != null ? tmp : new NewsClip[0]);

    mDrawables = new Drawable[3];
    for (int i = 0; i < 3; i++) {
      NewsClip clip = mNewsEntries[i];
      try {
        URL url = new URL(clip.getImageLocation());
        mDrawables[i] = new BitmapDrawable(url.openStream());
      } catch (Exception e) {
        mDrawables[i] = mStub;
      }
    }
  }

  public int getCount() {
    return 3;
  }

  public Object getItem(int position) {
    return null;
  }

  public long getItemId(int position) {
    return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView;
    if (convertView == null) { // if it's not recycled, initialize some
                               // attributes
      imageView = new ImageView(mContext);
      imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setPadding(8, 8, 8, 8);
    } else {
      imageView = (ImageView) convertView;
    }
    imageView.setImageDrawable(mDrawables[position]);
    return imageView;
  }
}