package dk.iha.noteos;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Gateway extends ListActivity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    SensorAdapter adapter = new SensorAdapter(this);
    setListAdapter(adapter);

    adapter.add(new TemperatureSensor(adapter, this));
    adapter.add(new TemperatureSensor(adapter, this));

    ListView lv = getListView();
    lv.setTextFilterEnabled(true);

    lv.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), getListAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private class SensorAdapter extends ArrayAdapter<SensorViewer> {

    public SensorAdapter(Context context) {
      super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return getItem(position).getView(getContext(), convertView, parent);
    }
  }
}