package dk.znz.drupdate;

import java.io.File;

public class Utils {
  static File getExternalCacheDirectory() {
    return new File(android.os.Environment.getExternalStorageDirectory(), "/Android/data/" + DRUpdateWidget.class.getPackage().getName() + "/cache/");
  }
}
