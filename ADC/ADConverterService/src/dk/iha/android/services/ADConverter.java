package dk.iha.android.services;

import android.app.Activity;
import android.os.Bundle;

public class ADConverter extends Activity {
	
	public static final String ADCONVERTER_SERVICE = ADConverter.class.getName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}