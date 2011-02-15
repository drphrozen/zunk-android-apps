package dk.iha.opencare;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.inject.Inject;
import com.tomgibara.android.util.SquareGridLayout;

public class Home extends RoboActivity {

	@InjectView(R.layout.main) private SquareGridLayout mSquareGridLayout;
	@Inject private LayoutInflater              mLayoutInflater;

	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
	}

}