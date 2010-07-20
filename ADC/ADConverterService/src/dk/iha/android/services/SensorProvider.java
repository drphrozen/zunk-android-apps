package dk.iha.android.services;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface SensorProvider {
	String getName();
	
}

class SensorManager {
	public final String ADC_PROVIDER = "ADC_PROVIDER";
	public final String DAC_PROVIDER = "DAC_PROVIDER";
	public final String GPIO_PROVIDER = "GPIO_PROVIDER";
	
	private final Set<IntegerSensorListener> sensors = Collections.synchronizedSet(new HashSet<IntegerSensorListener>());
	
	public void requestIntegerSensorUpdates (String provider, int index, IntegerSensorListener listener) {
		
		if(provider == ADC_PROVIDER) {
			if(index < 0 || index > 7) return;
			File sensorFile = new File("/dev/adc" + index);
			if(sensorFile.canRead() == false) return;
			
		} else if(provider == DAC_PROVIDER) {
			
		} else if(provider == GPIO_PROVIDER) {
			
		} else {
			return;
		}
	}
	
	public void removeIntegerSensorUpdates(IntegerSensorListener listener) {
		sensors.remove(listener);
	}
}

interface IntegerSensorListener {
	
}
