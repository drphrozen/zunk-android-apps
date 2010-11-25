package dk.znz.drupdate;

public interface NewsClipStore {
  NewsClip[] getClips();
  void add(NewsClip clip);
  void clear();
}
