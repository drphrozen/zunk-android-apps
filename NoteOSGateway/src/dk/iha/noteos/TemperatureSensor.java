package dk.iha.noteos;

import java.nio.ByteBuffer;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TemperatureSensor extends SensorBase {

  private float mTemperature;
  private View mView;
  private TextView mDescription;
  private Activity mActivity; 

  public TemperatureSensor(BaseAdapter adapter, Activity activity) {
    super(adapter);
    
    mTemperature = Float.NaN;
    mActivity = activity;
    mView = null;
    mDescription = null;
    
    new Thread(new Runnable() {
      @Override
      public void run() {
        Random r = new Random();
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.mark();
        while(true) {
          buffer.reset();
          onSensorData(buffer.putFloat(37.5f - r.nextFloat()).array());
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }
  
  @Override
  public View getView(Context context, View convertView, ViewGroup parent) {
    if(mView != null)
      return mView;
    mView = ViewGroup.inflate(context, R.layout.sensor_list_item, null);
    ImageView imageView = (ImageView) mView.findViewById(R.id.icon);
    imageView.setImageResource(R.drawable.thermometer);
    TextView titleView = (TextView) mView.findViewById(R.id.title);
    titleView.setText("Temperature Sensor");
    mDescription = (TextView) mView.findViewById(R.id.description);
    mDescription.setText(toString());
    return mView;
  }

  @Override
  public void onSensorData(byte[] data) {
    mTemperature = ByteBuffer.wrap(data).getFloat();
    if(mView == null) return;
    
    mActivity.runOnUiThread(new Runnable() {
      
      @Override
      public void run() {
//        mAdapter.notifyDataSetChanged();
        mDescription.setText(TemperatureSensor.this.toString());
      }
    });
  }
  
  @Override
  public String toString() {
    return String.format("%.1f\u00B0 C", mTemperature);
  }
}
