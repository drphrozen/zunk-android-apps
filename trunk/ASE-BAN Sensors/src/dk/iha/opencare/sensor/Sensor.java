package dk.iha.opencare.sensor;

public abstract class Sensor {
  protected final SensorChannel mSensorChannel;
  /**
   * Creates a new Sensor and connects it to a SensorChannel.
   * The sensor channel is backed by a single byte[] and is implemented as a circular buffer  
   * @param buffers the number of required buffers
   * @param bufferSize the size of each buffer
   */
  public Sensor(int buffers, int bufferSize) {
    mSensorChannel = new SensorChannel(buffers, bufferSize);
  }
}
