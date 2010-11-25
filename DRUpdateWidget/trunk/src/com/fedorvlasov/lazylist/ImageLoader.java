package com.fedorvlasov.lazylist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import dk.znz.drupdate.DRUpdateWidget;
import dk.znz.drupdate.R;

public class ImageLoader {

  // the simplest in-memory cache implementation. This should be replaced with
  // something like SoftReference or BitmapOptions.inPurgeable(since 1.6)
  private HashMap<URI, Bitmap> cache = new HashMap<URI, Bitmap>();

  private File                 cacheDir;

  public ImageLoader(Context context) {
    // Make the background thead low priority. This way it will not affect the
    // UI performance
    photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

    // Find the dir to save cached images
    if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
      cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/Android/data/" + DRUpdateWidget.class.getPackage().getName() + "/cache/");
    else
      cacheDir = context.getCacheDir();
    if (!cacheDir.exists())
      cacheDir.mkdirs();
  }

  final int stub_id = R.drawable.stub;

  public void DisplayImage(URI imageUri, Activity activity, ImageView imageView) {
    if (cache.containsKey(imageUri))
      imageView.setImageBitmap(cache.get(imageUri));
    else {
      queuePhoto(imageUri, activity, imageView);
      imageView.setImageResource(stub_id);
    }
  }

  private void queuePhoto(URI imageUri, Activity activity, ImageView imageView) {
    // This ImageView may be used for other images before. So there may be some
    // old tasks in the queue. We need to discard them.
    photosQueue.Clean(imageView);
    PhotoToLoad p = new PhotoToLoad(imageUri, imageView);
    synchronized (photosQueue.photosToLoad) {
      photosQueue.photosToLoad.push(p);
      photosQueue.photosToLoad.notifyAll();
    }

    // start thread if it's not started yet
    if (photoLoaderThread.getState() == Thread.State.NEW)
      photoLoaderThread.start();
  }

  private Bitmap getBitmap(URI uri) {
    // I identify images by hashcode. Not a perfect solution, good for the demo.
    String filename = String.valueOf(uri.hashCode());
    File f = new File(cacheDir, filename);

    // from SD cache
    Bitmap b = decodeFile(f);
    if (b != null)
      return b;

    // from web
    try {
      Bitmap bitmap = null;
      InputStream is = uri.toURL().openStream();
      OutputStream os = new FileOutputStream(f);
      Utils.CopyStream(is, os);
      os.close();
      bitmap = decodeFile(f);
      return bitmap;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  // decodes image and scales it to reduce memory consumption
  private Bitmap decodeFile(File f) {
    try {
      // decode image size
      BitmapFactory.Options o = new BitmapFactory.Options();
      o.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(new FileInputStream(f), null, o);

      // Find the correct scale value. It should be the power of 2.
      final int REQUIRED_SIZE = 70;
      int width_tmp = o.outWidth, height_tmp = o.outHeight;
      int scale = 1;
      while (true) {
        if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
          break;
        width_tmp /= 2;
        height_tmp /= 2;
        scale++;
      }

      // decode with inSampleSize
      BitmapFactory.Options o2 = new BitmapFactory.Options();
      o2.inSampleSize = scale;
      return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
    } catch (FileNotFoundException e) {
    }
    return null;
  }

  // Task for the queue
  private class PhotoToLoad {
    public URI       uri;
    public ImageView imageView;

    public PhotoToLoad(URI u, ImageView i) {
      uri = u;
      imageView = i;
    }
  }

  PhotosQueue photosQueue = new PhotosQueue();

  public void stopThread() {
    photoLoaderThread.interrupt();
  }

  // stores list of photos to download
  class PhotosQueue {
    private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

    // removes all instances of this ImageView
    public void Clean(ImageView image) {
      for (int j = 0; j < photosToLoad.size();) {
        if (photosToLoad.get(j).imageView == image)
          photosToLoad.remove(j);
        else
          ++j;
      }
    }
  }

  class PhotosLoader extends Thread {
    public void run() {
      try {
        while (true) {
          // thread waits until there are any images to load in the queue
          if (photosQueue.photosToLoad.size() == 0)
            synchronized (photosQueue.photosToLoad) {
              photosQueue.photosToLoad.wait();
            }
          if (photosQueue.photosToLoad.size() != 0) {
            PhotoToLoad photoToLoad;
            synchronized (photosQueue.photosToLoad) {
              photoToLoad = photosQueue.photosToLoad.pop();
            }
            Bitmap bmp = getBitmap(photoToLoad.uri);
            cache.put(photoToLoad.uri, bmp);
            URI uri = null;
            try {
              uri = new URI(photoToLoad.imageView.getTag().toString());
            } catch (URISyntaxException e) {
              e.printStackTrace();
            }
            if (uri != null && uri.equals(photoToLoad.uri)) {
              BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
              Activity a = (Activity) photoToLoad.imageView.getContext();
              a.runOnUiThread(bd);
            }
          }
          if (Thread.interrupted())
            break;
        }
      } catch (InterruptedException e) {
        // allow thread to exit
      }
    }
  }

  PhotosLoader photoLoaderThread = new PhotosLoader();

  // Used to display bitmap in the UI thread
  class BitmapDisplayer implements Runnable {
    Bitmap    bitmap;
    ImageView imageView;

    public BitmapDisplayer(Bitmap b, ImageView i) {
      bitmap = b;
      imageView = i;
    }

    public void run() {
      if (bitmap != null)
        imageView.setImageBitmap(bitmap);
      else
        imageView.setImageResource(stub_id);
    }
  }

  public void clearCache() {
    // clear memory cache
    cache.clear();

    // clear SD cache
    File[] files = cacheDir.listFiles();
    for (File f : files)
      f.delete();
  }

}
