package dk.znz.drupdate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.znz.serialization.SimpleSerializable;


public class DefaultNewsClipStore implements NewsClipStore, SimpleSerializable {

  private static final long serialVersionUID = 7042704507678009466L;

  private ArrayList<NewsClip> mClips;

  public NewsClip[] getClips() {
    return mClips.toArray(new NewsClip[0]);
  }

  public void add(NewsClip entry) {
    mClips.add(entry);
  }

  public void clear() {
    mClips.clear();
  }

  @Override
  public void serialize(DataOutputStream output) throws IOException {
    List<NewsClip> list = Collections.unmodifiableList(mClips);
    output.writeInt(list.size());
    for (NewsClip clip : list) {
      clip.serialize(output);
    }
  }

  @Override
  public void deserialize(DataInputStream input) throws IOException {
    final int length = input.readInt();
    ArrayList<NewsClip> list = new ArrayList<NewsClip>(length);
    for (int i = 0; i < length; i++) {
      NewsClip clip = new NewsClip();
      clip.deserialize(input);
      list.add(clip);
    }
    mClips = list;
  }
}

