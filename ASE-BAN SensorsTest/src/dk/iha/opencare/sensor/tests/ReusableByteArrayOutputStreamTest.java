/**
 * 
 */
package dk.iha.opencare.sensor.tests;


import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.iha.opencare.sensor.ReusableByteArrayOutputStream;

/**
 * @author drphrozen
 *
 */
public class ReusableByteArrayOutputStreamTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void testWrite() {
    byte[] buffer = new byte[10];
    byte[] expected = new byte[] {
        1,2,3,5,6,0,0,0,0,0
    };
    
    byte[][] testBuffers = new byte[][] { {1}, {2,3}, {4,5,6} };
    
    ReusableByteArrayOutputStream stream = new ReusableByteArrayOutputStream(buffer, 0, buffer.length);
    try {
      stream.write(testBuffers[0][0]);
      stream.write(testBuffers[1]);
      stream.write(testBuffers[2], 1, 2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Assert.assertEquals(Utils.compareArrays(expected, buffer), -1);
  }
  
  @Test(expected= IOException.class)
  public void testWriteIOException() throws IOException {
    byte[] buffer = new byte[10];
    ReusableByteArrayOutputStream stream = new ReusableByteArrayOutputStream(buffer, 0, buffer.length);
    try {
      for (int i = 0; i < buffer.length; i++) {
        stream.write(1);
      }
    } catch (IOException e) {
      Assert.fail("Write failed before buffer overrun!");
    }
    stream.write(1);
  }
}
