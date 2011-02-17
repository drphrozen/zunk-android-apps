package dk.iha.opencare;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.view.View;

public class HomePresentationModel extends Observable implements Observer {
  private final ArrayList<SensorPresentationModel> mList;
  
  public HomePresentationModel(ArrayList<SensorPresentationModel> list, View homeView) {
    mList = list;
  }
  
  public void addSensorPresentationModel(SensorPresentationModel model) {
    model.addObserver(this);
    mList.add(model);
  }

  public void removeSensorPresentationModel(SensorPresentationModel model) {
    model.deleteObserver(this);
    mList.remove(model);
  }
  
  public void showSensor(int id) {
    mList.get(id).showView();
  }

  @Override public void update(Observable observable, Object o) {
    if((observable instanceof SensorPresentationModel) == false) return;
    SensorPresentationModel model = (SensorPresentationModel)observable;
    notifyObservers(model.getId());
  }
}
