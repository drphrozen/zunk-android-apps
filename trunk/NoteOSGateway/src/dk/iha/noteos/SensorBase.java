package dk.iha.noteos;

import android.widget.BaseAdapter;


public abstract class SensorBase implements SensorViewer, OnSensorDataListener {
  
  protected final BaseAdapter mAdapter;
  
  public SensorBase(BaseAdapter adapter) {
    mAdapter = adapter;
  }
}
