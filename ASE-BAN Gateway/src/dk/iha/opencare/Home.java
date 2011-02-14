package dk.iha.opencare;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridView;

public class Home extends RoboActivity {

	@InjectView(R.id.gridview) private GridView mGridView;
	@Inject private LayoutInflater              mLayoutInflater;

	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mGridView.setAdapter(new ButtonAdapter(mLayoutInflater));
	}

}