package dk.iha.opencare;

import dk.iha.opencare.HomeButton.Status;

public class StatusChangedEvent {
  private final Status mStatus;

  public StatusChangedEvent(Status status) {
    mStatus = status;
  }

  public Status getStatus() {
    return mStatus;
  }
}
