package dk.iha.opencare;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class HomeButton {

  private final OnClickListener mOnClickListener;
  private ImageButton mButton;
  private FrameLayout mFrameLayout;
  private View mView;
  private Status mStatus;
  
  public HomeButton(OnClickListener listener) {
    mOnClickListener = listener;
  }
  
  public void setView(View view) {
    if(mView == view) return;
    mView = view;
    mButton = (ImageButton)view.findViewById(R.id.home_button);
    mButton.setOnClickListener(mOnClickListener);
    mFrameLayout = (FrameLayout)view.findViewById(R.id.home_button_frame);
  }
  
  public void setStatus(Status s) {
    mStatus = s;
  }
  
  public static View createView(LayoutInflater inflater) {
    return inflater.inflate(R.layout.home_button, null);
  }
  
  public void updateView() {
    Resources resources = mView.getContext().getResources();
    switch(mStatus) {
    case Error:
      mButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_red));
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_error));
      break;
    case Info:
      mButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_green));
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_information));
      break;
    case Normal:
      mButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_green));
      mFrameLayout.setForeground(null);
      break;
    case Warning:
      mButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_yellow));
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_warning));      
      break;
    }
  }
  
  public ImageButton getButton() {
    return mButton;
  }
  
  public View getView() {
    return mView;
  }

  public enum Status {
    Normal,
    Info,
    Warning,
    Error
  }
}
