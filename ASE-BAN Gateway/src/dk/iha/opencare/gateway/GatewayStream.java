package dk.iha.opencare.gateway;

import java.io.IOException;
import java.io.OutputStream;

public class GatewayStream extends OutputStream {

  @Override
  public void write(int oneByte) throws IOException {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void write(byte[] buffer) throws IOException {
    // TODO Auto-generated method stub
    super.write(buffer);
  }
  
  @Override
  public void write(byte[] buffer, int offset, int count) throws IOException {
    // TODO Auto-generated method stub
    super.write(buffer, offset, count);
  }
}
