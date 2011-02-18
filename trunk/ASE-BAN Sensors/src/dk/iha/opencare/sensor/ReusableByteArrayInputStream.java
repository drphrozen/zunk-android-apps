package dk.iha.opencare.sensor;

import java.io.ByteArrayInputStream;

public class ReusableByteArrayInputStream extends ByteArrayInputStream {
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