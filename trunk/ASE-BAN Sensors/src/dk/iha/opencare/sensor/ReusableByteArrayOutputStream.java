package dk.iha.opencare.sensor;

import java.io.IOException;
import java.io.OutputStream;

public class ReusableByteArrayOutputStream extends OutputStream {
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