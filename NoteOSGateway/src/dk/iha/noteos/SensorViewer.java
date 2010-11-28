package dk.iha.noteos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface SensorViewer {
  View getView(Context context, View convertView, ViewGroup parent);
}
