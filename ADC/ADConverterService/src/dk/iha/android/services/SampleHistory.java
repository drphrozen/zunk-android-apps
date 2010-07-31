package dk.iha.android.services;

public class SampleHistory {
	private final int[] history;
	private int min;
	private int max;
	private int index;
	
	public SampleHistory(int length) {
		history = new int[length];
		for (int i = 0; i < history.length; i++) {
			history[i] = 0;
		}
		min = 0;
		max = 0;
		index = 0;
	}
	
	public void put(int value) {
		final int nextIndex = (index + 1)%history.length;
		final int oldValue = history[nextIndex];
		history[nextIndex] = value;
		if(oldValue == max || oldValue == min) {
			update();
		}
	}

	private void update() {
		min = Integer.MAX_VALUE;
		max = Integer.MIN_VALUE;
		for (int i = 0; i < history.length; i++) {
			int value = history[i];
			if(value < min)
				min = value;
			if(value > max)
				max = value;
		}
	}
}
