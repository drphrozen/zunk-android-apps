package dk.iha.opencare.sensor.tests;

import static dk.iha.opencare.sensor.tests.Utils.compareArrays;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.iha.opencare.sensor.Buffer;

public class BufferTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testBuffer() {
    Buffer buffer = new Buffer(8, 4);
    assertEquals(4, buffer.length);
    buffer = new Buffer(8, 8);
    assertEquals(8, buffer.length);
    buffer = new Buffer(8, 12);
    assertEquals(12, buffer.length);
    buffer = new Buffer(8, 0);
    assertEquals(0, buffer.length);
  }

  @Test
  public void testGetStreams() throws IOException {
    byte[] tmp = new byte[4];
    Buffer buffer = new Buffer(4, 4);
    byte index = 0;
    for (int i = 0; i < buffer.length; i++) {
      for (int j = 0; j < tmp.length; j++)
        tmp[j] = index++;
      OutputStream output = buffer.getOutputStream(i);
      output.write(tmp);
    }
    index = 0;
    byte[] buf = new byte[4];
    for (int i = 0; i < buffer.length; i++) {
      for (int j = 0; j < tmp.length; j++)
        tmp[j] = index++;
      InputStream input = buffer.getInputStream(i);
      assertEquals(4, input.read(buf));
      assertEquals(-1, compareArrays(tmp, buf));
    }
  }
}
