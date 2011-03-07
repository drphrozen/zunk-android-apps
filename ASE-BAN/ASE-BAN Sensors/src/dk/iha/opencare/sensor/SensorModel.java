//package dk.iha.opencare.sensor;
//
//import java.io.FilterInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class SensorModel {
//
//  public SensorChannel createSensorChannel(int buffers, int bufferSize) {
//    mSensorChannels
//    return null;
//  }
//}
//
//class Buffer {
//	private final byte[] mData;
//	private final int mSize;
//	private final ByteArrayInputStream[] streams;
//
//	public Buffer(int size, int count) {
//		mSize = size;
//		mData = new byte[size * count];
//		streams = new ByteArrayInputStream[count];
//		for (int i = 0; i < streams.length; i++) {
//			streams[i] = new ByteArrayInputStream(mData, i * mSize, mSize);
//		}
//	}
//}
//
//class ByteArrayInputStream extends InputStream {
//  @Override
//  public int read() throws IOException {
//    return 0;
//  }
//}
//
//class CircularInputStream implements Iterator<ByteArrayInputStream> {
//
//  private final byte[] mData;
//  private final int mSize;
//  private int mPosition;
//  
//  @Override
//  public boolean hasNext() {
//    return false;
//  }
//
//  @Override
//  public ByteArrayInputStream next() {
//    return null;
//  }
//
//  @Override
//  public void remove() {
//    
//  }
//
//}
