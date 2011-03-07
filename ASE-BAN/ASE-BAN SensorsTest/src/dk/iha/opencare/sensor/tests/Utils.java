package dk.iha.opencare.sensor.tests;

public class Utils {
  /**
   * 
   * @param first
   * @param second
   * @return -1 on success, -2 if length differs, otherwise the index that failed
   */
  public static int compareArrays(byte[] first, byte[] second) {
    if(first.length != second.length) return -2;
    for (int i = 0; i < first.length; i++) {
      if(first[i] != second[i])
        return i;
    }
    return -1;
  }

}
