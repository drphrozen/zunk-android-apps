package dk.znz;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

public class DRClip {
  private UUID   mQid;
  private String mTitle;
  private URI    mMvsUrl;
  private URI    mImage;
  private Date   mPubDate;
  private String mRssDate;
  private String mDescription;

  public UUID getQid() {
    return mQid;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public URI getMvsUrl() {
    return mMvsUrl;
  }

  public void setMvsUrl(URI mvsUrl) {
    mMvsUrl = mvsUrl;
  }

  public URI getImage() {
    return mImage;
  }

  public void setImage(URI image) {
    mImage = image;
  }

  public Date getPubDate() {
    return mPubDate;
  }

  public void setPubDate(Date pubDate) {
    mPubDate = pubDate;
  }

  public String getRssDate() {
    return mRssDate;
  }

  public void setRssDate(String rssDate) {
    mRssDate = rssDate;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public void setQid(UUID qid) {
    mQid = qid;
  }
}