package dk.iha.opencare;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HomeButtonAdapter extends BaseAdapter {

  private final ArrayList<HomeButton> mItems;
  private final LayoutInflater mLayoutInflater;
  
  public HomeButtonAdapter(ArrayList<HomeButton> list, LayoutInflater inflater) {
    mItems = list;
    mLayoutInflater = inflater;
  }

  @Override
  public int getCount() {
    return mItems.size();
  }

  @Override
  public Object getItem(int position) {
    return mItems.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null)
      convertView = HomeButton.createView(mLayoutInflater);
    HomeButton homeButton = mItems.get(position);
    homeButton.setView(convertView);
    homeButton.updateView();
    return convertView;
  }
}
