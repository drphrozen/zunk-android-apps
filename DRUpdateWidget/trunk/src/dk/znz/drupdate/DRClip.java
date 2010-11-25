package dk.znz.drupdate;

import java.util.UUID;

public class DRClip extends NewsClip {

  private static final long serialVersionUID = -7750805380544336640L;
  
  private UUID   mQid;
  private String mRssDate;

  public UUID getQid() {
    return mQid;
  }

  public String getRssDate() {
    return mRssDate;
  }

  public void setRssDate(String rssDate) {
    mRssDate = rssDate;
  }

  public void setQid(UUID qid) {
    mQid = qid;
  }
}