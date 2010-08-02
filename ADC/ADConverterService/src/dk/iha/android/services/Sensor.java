package dk.iha.android.services;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Sensor {
	public final static String ADC_PROVIDER = "ADC_PROVIDER";
	public final static String DAC_PROVIDER = "DAC_PROVIDER";
	public final static String GPIO_PROVIDER = "GPIO_PROVIDER";
	
	private final IntegerReader reader;
	
	/**
	 * Creates a sensor to read from, this is NOT thread safe!
	 * @param provider The type of sensor you wan't to read on ADC_PROVIDER, DAC_PROVIDER, GPIO_PROVIDER which translates to /dev/adc#, /dev/dac#, /dev/gpio#
	 * @param index The specific provider eg.: /dev/adc[0-7]
	 * @throws FileNotFoundException if the combination of provider and index doesn't exist
	 * @throws IllegalArgumentException if provider is not supported
	 * @throws IllegalArgumentException if the index is invalid
	 */
	Sensor(String provider, int index) throws FileNotFoundException, IllegalArgumentException {
		if(provider == ADC_PROVIDER) {
			if(index < 0 || index > 7)
				throw new IllegalArgumentException("The given index is not valid.");;
			final File sensorFile = new File("/dev/adc" + index);
			reader = new IntegerReader() {
				private final DataInputStream stream = new DataInputStream(new FileInputStream(sensorFile));
				public int readInteger() throws IOException {
					return Short.reverseBytes(stream.readShort());
				}
			};
		} else if(provider == DAC_PROVIDER) {
			//TODO: implement DAC
			throw new IllegalArgumentException("DAC is not implemented yet!");
		} else if(provider == GPIO_PROVIDER) {
			//TODO: implement GPIO
			throw new IllegalArgumentException("GPIO is not implemented yet!");
		} else {
			throw new IllegalArgumentException("Unsupported provider given!");
		}
	}
	
	public int readInteger() throws IOException {
		return reader.readInteger();
	}
}
