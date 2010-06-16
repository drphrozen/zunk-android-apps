package dk.iha.testads;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class testads extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final TextView textView = (TextView)findViewById(R.id.TextView01);
		textView.setText(ByteOrder.nativeOrder().toString());
		
		final Button button = (Button)findViewById(R.id.Button01);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				
				try {
					DataReader input = new DataReader(new FileInputStream("/dev/adc0")); // 
					sb.append(input.readUnsignedShort() + "\n");
					input.close();
				} catch (IOException e) {
//					ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
//					PrintStream printStream = new PrintStream(byteArrayStream);
//					e.printStackTrace(printStream);
//					sb.append(byteArrayStream);
					e.printStackTrace();
				}
				textView.setText(sb.toString());
			}
		});
	}
}

class input_event {
	input_event(FileInputStream stream) {
		FileChannel channel = stream.getChannel();
		
	}
  long time; // uint32, timeval
  int type; // uint16 
  int code; // uint16
  long value; // uint32
};
