package dk.iha.opencare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class SquareImageButton extends ImageButton {

  public SquareImageButton(Context context) {
    super(context);
  }

  public SquareImageButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public SquareImageButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    this.setMeasuredDimension(parentWidth, parentWidth);
  }

}
