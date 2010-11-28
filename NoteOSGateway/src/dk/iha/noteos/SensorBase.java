package dk.iha.noteos;

import android.os.Handler;


public abstract class SensorBase implements SensorViewer, OnSensorDataListener {

  protected Handler mHandler;
  
  public SensorBase() {
    mHandler = new Handler();
  }
}
