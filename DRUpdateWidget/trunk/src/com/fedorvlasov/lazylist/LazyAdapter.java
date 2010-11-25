package com.fedorvlasov.lazylist;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dk.znz.drupdate.NewsClip;
import dk.znz.drupdate.R;

public class LazyAdapter extends BaseAdapter {

  private Activity              activity;
  private NewsClip[]            data;
  private static LayoutInflater inflater = null;
  public ImageLoader            imageLoader;

  public LazyAdapter(Activity a, NewsClip[] ne) {
    activity = a;
    data = ne;
    inflater = (LayoutInflater) activity
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    imageLoader = new ImageLoader(activity.getApplicationContext());
  }

  public int getCount() {
    return data.length;
  }

  public Object getItem(int position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  public static class ViewHolder {
    public TextView  text;
    public TextView  description;
    public ImageView image;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    View vi = convertView;
    ViewHolder holder;
    if (convertView == null) {
      vi = inflater.inflate(R.layout.item, null);
      holder = new ViewHolder();
      holder.text = (TextView) vi.findViewById(R.id.text);
      holder.description = (TextView) vi.findViewById(R.id.description);
      holder.image = (ImageView) vi.findViewById(R.id.image);
      vi.setTag(holder);
    } else
      holder = (ViewHolder) vi.getTag();
    final NewsClip entry = data[position];
    holder.text.setText(entry.getTitle());
    holder.description.setText(entry.getDescription());
    holder.image.setTag(entry.getImageLocation());
    URI uri = null;
    try {
      uri = new URI(entry.getImageLocation());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    imageLoader.DisplayImage(uri, activity, holder.image);
    vi.setOnClickListener(new OnNewsClipClicked(activity, entry));
    return vi;
  }
}