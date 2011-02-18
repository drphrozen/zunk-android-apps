package dk.iha.opencare.wban;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Used to split a single stream to several streams, if a stream throws an
 * exception then the stream is silently removed.
 * 
 * @author drphrozen
 * 
 */
public class SplitStream extends OutputStream {

  protected final LinkedList<OutputStream> mOuts = new LinkedList<OutputStream>();
  protected final ReentrantLock            mLock = new ReentrantLock(true);

  public void addOutputStream(OutputStream outputStream) {
    mLock.lock();
    try {
      mOuts.add(outputStream);
    } finally {
      mLock.unlock();
    }
  }

  public void removeOutputStream(OutputStream outputStream) {
    mLock.lock();
    try {
      mOuts.remove(outputStream);
    } finally {
      mLock.unlock();
    }
  }

  public SplitStream() { }
  
  public SplitStream(OutputStream... outs) {
    mOuts.addAll(Arrays.asList(outs));
  }

  @Override
  public void write(int b) throws SplitIOException {
    mLock.lock();
    try {
      for (Iterator<OutputStream> iterator = mOuts.iterator(); iterator.hasNext();) {
        OutputStream outputStream = iterator.next();
        try {
          outputStream.write(b);
        } catch (IOException e) {
          // TODO: handle exception
          iterator.remove();
        }
      }
    } finally {
      mLock.unlock();
    }
  }

  @Override
  public void write(byte[] b, int off, int len) throws SplitIOException {
    LinkedList<IOException> exceptions = null;
    mLock.lock();
    try {
      for (Iterator<OutputStream> iterator = mOuts.iterator(); iterator.hasNext();) {
        OutputStream outputStream = iterator.next();
        try {
          outputStream.write(b, off, len);
        } catch (IOException e) {
          if(exceptions == null)
             exceptions = new LinkedList<IOException>();
          exceptions.add(e);
          iterator.remove();
        }
      }
    } finally {
      mLock.unlock();
    }
    if(exceptions != null)
      throw new SplitIOException(exceptions);
  }
  
  public static class SplitIOException extends IOException {

    private static final long serialVersionUID = 1786727867232415158L;

    public final List<IOException> exceptions;
    
    public SplitIOException(List<IOException> exceptions) {
      this.exceptions = exceptions;
    }
  }
}
