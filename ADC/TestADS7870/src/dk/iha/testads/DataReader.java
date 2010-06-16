package dk.iha.testads;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReader extends FilterInputStream {

	private final DataInputStream stream;
	
	protected DataReader(InputStream in) {
		super(in);
		stream = new DataInputStream(in);
	}
	
	public long readUnsignedInteger() throws IOException {
		int i = stream.readInt();
		return Integer.reverseBytes(i) & 0xFFFFFFFFL;
	}
	
	public int readUnsignedShort() throws IOException {
		short s = stream.readShort();
		return Short.reverseBytes(s) & 0xFFFF;
	}
	
}