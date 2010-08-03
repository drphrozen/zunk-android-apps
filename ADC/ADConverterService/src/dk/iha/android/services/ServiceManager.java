package dk.iha.android.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceManager extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicemanager);
		
		((Button)findViewById(R.id.Button01)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ServiceManager.this, ADConverter.class); 
				ServiceManager.this.startService(intent);
			}
		});
		((Button)findViewById(R.id.Button02)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ServiceManager.this, ADConverter.class); 
				ServiceManager.this.stopService(intent);
			}
		});
	}
	
}
