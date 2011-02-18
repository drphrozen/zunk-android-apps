package dk.iha.opencare.wban;

public class Event {
  public final byte[] data;
  public final int offset;
  public final int length;

  public Event(byte[] data, int offset, int length) {
    this.data = data;
    this.offset = offset;
    this.length = length;
  }
}
