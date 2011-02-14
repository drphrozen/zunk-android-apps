package dk.iha.opencare;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.GridView;

public class Home extends RoboActivity {

  @InjectView(R.id.gridview) GridView mGridView;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mGridView.setAdapter(new ButtonAdapter(this));
  }

}