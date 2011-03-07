package dk.iha.opencare;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.inject.Inject;

import dk.iha.opencare.HomeButton.Status;

public class Home extends RoboActivity {

  @InjectView(R.id.maingrid) private GridView mGridView;
  @Inject private LayoutInflater              mLayoutInflater;
  private ArrayList<HomeButton>               mHomeButtons;

  /** Called when the activity is first created. */
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mHomeButtons = new ArrayList<HomeButton>(16);

    mGridView.setAdapter(new HomeButtonAdapter(mHomeButtons, mLayoutInflater));

    Status[] status = new Status[] { Status.Normal, Status.Info, Status.Warning, Status.Error };
    
    for(int i = 0; i<20; i++) {
      Status s = status[i%status.length];
      final String message = i + ": " + s.toString();
      HomeButton homeButton = new HomeButton(new OnClickListener() {
        @Override public void onClick(View v) {
          Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
      });
      homeButton.setStatus(s);
      mHomeButtons.add(homeButton);      
    }
  }
  
  public void onStatusChanged(@Observes StatusChangedEvent event) {
    
  }
}