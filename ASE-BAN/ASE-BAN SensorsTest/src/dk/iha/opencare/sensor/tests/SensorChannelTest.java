/**
 * 
 */
package dk.iha.opencare.sensor.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.iha.opencare.sensor.SensorChannel;

/**
 * @author drphrozen
 * 
 */
public class SensorChannelTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception { }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception { }

  /**
   * Test method for
   * {@link dk.iha.opencare.sensor.SensorChannel#SensorChannel(int, int)}.
   */
  @Test
  public void testSensorChannel() {
    SensorChannel sensorChannel = new SensorChannel(4, 4);
    assertNotNull(sensorChannel);
  }

  /**
   * Test method for
   * {@link dk.iha.opencare.sensor.SensorChannel#WaitForChannel(dk.iha.opencare.sensor.SensorChannel.Callback)}
   * .
   */
  @Test
  public void testWaitForChannel() {
    new SensorChannel(4, 4).WaitForChannel(new SensorChannel.Callback() {
      @Override
      public void channelReady(Iterator<InputStream> input, Iterator<OutputStream> output) {
        assertTrue(input.hasNext() == false);
        assertTrue(output.hasNext());
        OutputStream outputStream = output.next();
        assertNotNull(outputStream);
        final byte[] bytes = new byte[] { 1, 2 };

        try {
          outputStream.write(1);

          outputStream.write(bytes);
          outputStream.write(bytes, 1, 1);

          assertTrue(input.hasNext());
          InputStream inputStream = input.next();
          
          assertEquals(1, inputStream.read());
          
          byte[] tmp = new byte[2];
          int length = inputStream.read(tmp);
          assertEquals(2, length);
          assertEquals(Utils.compareArrays(tmp, bytes), -1);

          tmp = new byte[] { 2 };
          byte[] tmp2 = new byte[1];
          length = inputStream.read(tmp2, 0, 1);
          assertEquals(1, length);
          assertEquals(Utils.compareArrays(tmp, tmp2), -1);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }
}
