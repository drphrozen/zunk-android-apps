package dk.iha.opencare.wban.tests;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.iha.opencare.wban.SplitStream;

public class SplitStreamTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testSplitStream() {
    ByteArrayOutputStream stream = new ByteArrayOutputStream(2);
    SplitStream splitStream = new SplitStream();
    splitStream.addOutputStream(stream);
  }

}
