package dk.iha.opencare;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class HomeButton {

  private final Button mButton;
  private final FrameLayout mFrameLayout;
  private final View mView;
  
  public HomeButton(View view) {
    mView = view;
    mButton = (Button)view.findViewById(R.id.home_button);
    mFrameLayout = (FrameLayout)view.findViewById(R.id.home_button_frame);
  }
  
  public void setStatus(Status s) {
    Resources resources = mView.getContext().getResources();
    switch(s) {
    case Error:
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_error));
      break;
    case Info:
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_information));
      break;
    case Normal:
      mFrameLayout.setForeground(null);
      break;
    case Warning:
      mFrameLayout.setForeground(resources.getDrawable(R.drawable.icon_warning));      
      break;
    }
  }
  
  public Button getButton() {
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
