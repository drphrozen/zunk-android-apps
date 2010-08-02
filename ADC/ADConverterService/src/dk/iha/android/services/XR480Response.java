package dk.iha.android.services;


public class XR480Response {
	public final Long timestamp;
	public final String raw;
	public final String rssi;
	
	public XR480Response(Long timestamp, String raw, String rssi) {
		this.timestamp = timestamp;
		this.raw = raw;
		this.rssi = rssi;
	}
}