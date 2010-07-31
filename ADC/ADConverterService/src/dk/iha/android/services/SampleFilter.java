package dk.iha.android.services;

public class SampleFilter {
	private final int tolerance;
	private final int count;
	private int countAccepted;
	private int lastValue;
	
	/**
	 * A simple sample filter.
	 * @param tolerance The tolerated deviation between samples.
	 * @param count Number of consecutive tolerated samples to trigger a positive response.
	 */
	public SampleFilter(int tolerance, int count) {
		this.tolerance = tolerance;
		this.count = count;
	}
	
	/**
	 * Handles the next given sample.
	 * @param value The next sample.
	 * @return True when count was reached, otherwise false.
	 */
	public boolean put(int value) {
		final int delta = Math.abs(value - lastValue);
		lastValue = value;
		countAccepted = (delta <= tolerance ? ++countAccepted : 0);
		return (countAccepted == count);
	}
}