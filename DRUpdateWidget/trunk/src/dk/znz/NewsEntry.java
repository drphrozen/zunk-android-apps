package dk.znz;

import java.net.URI;

public class NewsEntry {

  private URI mImage;
  private String mName;
  private String mDescription;
  private URI mMedia;
  
  public URI getImage() {
    return mImage;
  }
  public void setImage(URI image) {
    mImage = image;
  }
  public String getName() {
    return mName;
  }
  public void setName(String name) {
    mName = name;
  }
  public String getDescription() {
    return mDescription;
  }
  public void setDescription(String description) {
    mDescription = description;
  }
  public URI getMedia() {
    return mMedia;
  }
  public void setMedia(URI uri) {
    mMedia = uri;
  }
}
