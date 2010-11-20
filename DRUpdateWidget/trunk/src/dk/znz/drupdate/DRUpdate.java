package dk.znz.drupdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

final class DRUpdate {
  private static final URI uri = URI.create("http://znz.dk/znk/drupdate.php");

  public static NewsEntry[] getNewsEntries() throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
    ArrayList<DRClip> clips = new DRClipParser().parse();
    NewsEntry[] out = new NewsEntry[clips.size()];
    int i = 0;
    for (DRClip clip : clips) {
      out[i] = new NewsEntry();
      out[i].setDescription(clip.getDescription());
      out[i].setImage(clip.getImage());
      out[i].setMedia(clip.getMvsUrl());
      out[i].setName(clip.getTitle());
      i++;
    }

    return out;
  }

  public static String getJSON() throws MalformedURLException, IOException {

    return getJSON(uri.toURL().openStream());
  }

  public static String getJSON(ProgressInfo progressInfo) throws MalformedURLException, IOException {
    return getJSON(new ProgressInputStream(uri.toURL().openStream(), progressInfo));
  }

  private static String getJSON(InputStream inputStream) throws IOException {
    InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    BufferedReader bufferedReader = new BufferedReader(reader);
    StringBuilder sb = new StringBuilder(50000);
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }
}
