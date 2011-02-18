package dk.iha.opencare.sensor;

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

}
