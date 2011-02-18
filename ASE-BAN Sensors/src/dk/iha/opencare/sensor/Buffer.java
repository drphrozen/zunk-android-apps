package dk.iha.opencare.sensor;

import java.io.InputStream;
import java.io.OutputStream;

public class Buffer {
  private final byte[]                        mData;
  private final int                           mSize;
  public final int                            length;
  private final ReusableByteArrayInputStream  mInputStream;
  private final ReusableByteArrayOutputStream mOutputStream;

  public Buffer(int size, int count) {
    this.mSize = size;
    this.length = count;
    mData = new byte[size * count];
    mInputStream = new ReusableByteArrayInputStream(mData, 0, size);
    mOutputStream = new ReusableByteArrayOutputStream(mData, 0, size);
  }

  private int getPosition(int index) {
    if (index > length || index < 0)
      throw new IndexOutOfBoundsException();
    return index * mSize;
  }

  public OutputStream getOutputStream(int index) {
    mOutputStream.reconfigure(getPosition(index), mSize);
    return mOutputStream;
  }

  public InputStream getInputStream(int index) {
    mInputStream.reconfigure(getPosition(index), mSize);
    return mInputStream;
  }
}