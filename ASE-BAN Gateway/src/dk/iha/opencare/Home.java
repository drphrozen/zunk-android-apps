package dk.iha.opencare;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.inject.Inject;
import com.tomgibara.android.util.SquareGridLayout;

import dk.iha.opencare.HomeButton.Status;

public class Home extends RoboActivity {

	@InjectView(R.id.maingrid) private SquareGridLayout mSquareGridLayout;
	@Inject private LayoutInflater              mLayoutInflater;

	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
    HomeButton homeButton = createHomeButton();
    homeButton.setStatus(Status.Normal);
    mSquareGridLayout.addView(homeButton.getView());
    homeButton = createHomeButton();
    homeButton.setStatus(Status.Info);
    mSquareGridLayout.addView(homeButton.getView());
    homeButton = createHomeButton();
    homeButton.setStatus(Status.Warning);
    mSquareGridLayout.addView(homeButton.getView());
    homeButton = createHomeButton();
    homeButton.setStatus(Status.Error);
    mSquareGridLayout.addView(homeButton.getView());
	}
	
	private HomeButton createHomeButton() {
    View view = mLayoutInflater.inflate(R.layout.home_button, null);
    return new HomeButton(view);
	}
}