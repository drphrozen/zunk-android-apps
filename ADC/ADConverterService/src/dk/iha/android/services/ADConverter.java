package dk.iha.android.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class ADConverter extends Activity {
	
	public static final String ADCONVERTER_SERVICE = "ADCONVERTER_SERVICE";
	
	private final int sampleRate = 10; // samples pr second
	
	private Thread thread = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		
    }
}