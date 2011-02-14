package dk.iha.opencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ButtonAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private final int[]          mInts = new int[] {
	                                     1, 2, 3, 4, 5, 6
	                                     };

	public ButtonAdapter(LayoutInflater layoutInflater) {
		mInflater = layoutInflater;
	}

	@Override public int getCount() {
		return mInts.length;
	}

	@Override public Object getItem(int position) {
		return mInts[position];
	}

	@Override public long getItemId(int position) {
		return mInts.length;
	}

	@Override public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.home_button, null);
		Button button = (Button)convertView.findViewById(R.id.button);
		StringBuilder sb = new StringBuilder();
		sb.append(mInts[position]);
		for (int i = 0; i < position; i++) {
	    sb.append('\n');
	    sb.append(mInts[position]);
    }
		button.setText(sb.toString());
		return convertView;
	}

}
