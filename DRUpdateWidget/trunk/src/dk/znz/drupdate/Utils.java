package dk.znz.drupdate;

import java.io.File;

public class Utils {
  static File getExternalCacheDirectory() {
    return new File(android.os.Environment.getExternalStorageDirectory(), "/Android/data/" + DRUpdateActivity.class.getPackage().getName() + "/cache/");
  }
}
