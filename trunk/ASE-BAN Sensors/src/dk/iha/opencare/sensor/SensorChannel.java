package dk.iha.opencare.sensor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class SensorChannel {

  private final Buffer                 mBuffer;
  private int                          mFront;
  private int                          mLength;
  private final ReentrantLock          lock = new ReentrantLock(true);
  private final Iterator<OutputStream> mOutputIterator;
  private final Iterator<InputStream>  mInputIterator;

  public SensorChannel(int buffers, int bufferSize) {
    mBuffer = new Buffer(bufferSize, buffers);
    mOutputIterator = new Iterator<OutputStream>() {
      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public OutputStream next() {
        mFront = (mFront + 1) % mBuffer.length;
        if (mLength < mBuffer.length)
          mLength++;
        return mBuffer.getOutputStream(mFront);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
    mInputIterator = new Iterator<InputStream>() {
      @Override
      public boolean hasNext() {
        return mLength > 0;
      }

      @Override
      public InputStream next() {
        int back = getBack();
        mLength--;
        return mBuffer.getInputStream(back);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public static interface Callback {
    void channelReady(Iterator<InputStream> input, Iterator<OutputStream> output);
  };

  public void WaitForChannel(Callback callback) {
    lock.lock();
    try {
      callback.channelReady(mInputIterator, mOutputIterator);
    } finally {
      lock.unlock();
    }
  }

  private int getBack() {
    return (mFront + 1 - mLength) % mBuffer.length;
  }

  private static class Buffer {
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

    private static class ReusableByteArrayInputStream extends ByteArrayInputStream {
      public ReusableByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
      }

      public void reconfigure(int offset, int length) {
        pos = offset;
        mark = offset;
        int tmp = offset + length;
        count = tmp > buf.length ? buf.length : tmp;
      }
    }

    private static class ReusableByteArrayOutputStream extends OutputStream {
      protected byte[] buf;
      protected int    count;
      protected int    pos;

      public ReusableByteArrayOutputStream(byte[] buf, int offset, int length) {
        this.buf = buf;
        reconfigure(offset, length);
      }

      public void reconfigure(int offset, int length) {
        pos = offset;
        int tmp = offset + length;
        count = tmp > buf.length ? buf.length : tmp;
      }

      @Override
      public void write(int oneByte) throws IOException {
        if (pos >= count)
          throw new IOException();
        buf[pos] = (byte) oneByte;
        pos++;
      }

      @Override
      public void write(byte[] buffer, int offset, int count) throws IOException {
        if ((offset < 0) || (offset > buffer.length) || (count < 0) ||
                         ((offset + count) > buffer.length) || ((offset + count) < 0)) {
          throw new IndexOutOfBoundsException();
        } else if (count == 0) {
          return;
        }
        int newCount = pos + count;
        if (newCount > this.count)
          throw new IOException();
        System.arraycopy(buffer, offset, buf, pos, count);
        pos += count;
      }
    }
  }

}
