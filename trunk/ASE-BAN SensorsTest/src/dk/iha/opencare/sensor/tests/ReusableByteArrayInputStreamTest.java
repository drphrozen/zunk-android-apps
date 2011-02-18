package dk.iha.opencare.sensor.tests;

import static org.junit.Assert.fail;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.iha.opencare.sensor.ReusableByteArrayInputStream;

public class ReusableByteArrayInputStreamTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testReusableByteArrayInputStream() {
    byte[] buffer = new byte[] { 1,2,3,4,5,6,7,8,9,0 };
    
    ReusableByteArrayInputStream stream = new ReusableByteArrayInputStream(buffer, 0, buffer.length);
    Assert.assertEquals(1, stream.read()); 
    byte[] tmp = new byte[3];
    try {
      Assert.assertEquals(3, stream.read(tmp));
    } catch (IOException e) {
      Assert.fail();
    }
    Assert.assertEquals(-1, Utils.compareArrays(tmp, new byte[] {2,3,4}));
    Assert.assertEquals(2, stream.read(tmp, 1, 2));
    Assert.assertEquals(-1, Utils.compareArrays(tmp, new byte[] {2,5,6}));
  }

  @Test
  public void testReconfigure() {
    fail("Not yet implemented");
  }

  @Test
  public void testRead() {
    fail("Not yet implemented");
  }

  @Test
  public void testReadByteArrayIntInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testSkip() {
    fail("Not yet implemented");
  }

  @Test
  public void testAvailable() {
    fail("Not yet implemented");
  }

  @Test
  public void testClose() {
    fail("Not yet implemented");
  }

  @Test
  public void testMark() {
    fail("Not yet implemented");
  }

  @Test
  public void testReset() {
    fail("Not yet implemented");
  }

  @Test
  public void testMarkSupported() {
    fail("Not yet implemented");
  }

  @Test
  public void testByteArrayInputStreamByteArray() {
    fail("Not yet implemented");
  }

  @Test
  public void testByteArrayInputStreamByteArrayIntInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testReadByteArray() {
    fail("Not yet implemented");
  }

}
