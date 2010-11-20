package dk.znz.drupdate;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DRClipHandler extends DefaultHandler {

  private final ArrayList<DRClip>           mArrayList              = new ArrayList<DRClip>(30);
  private DRClip                            mDRClip;

  private final Map<String, PropertySetter> mPropertySetters;
  private final SimpleDateFormat            mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private String                            mActiveElement;

  public DRClipHandler() {
    HashMap<String, PropertySetter> map = new HashMap<String, PropertySetter>(6);
    map.put("title", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) {
        drClip.setTitle(content);
      }
    });
    map.put("mvsUrl", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) {
        drClip.setMvsUrl(URI.create(content));
      }
    });
    map.put("image", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) {
        drClip.setImage(URI.create(content));
      }
    });
    map.put("pubDate", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) throws ParseException {
        drClip.setPubDate(mSimpleDateFormat.parse(content));
      }
    });
    map.put("rssDate", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) {
        drClip.setRssDate(content);
      }
    });
    map.put("description", new PropertySetter() {
      public void setProperty(String content, DRClip drClip) {
        drClip.setDescription(content);
      }
    });
    mPropertySetters = Collections.unmodifiableMap(map);
  }

  @Override
  public void startDocument() throws SAXException {
    getList().clear();
    super.startDocument();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    mActiveElement = qName;
    if (qName.equals("clip")) {
      mDRClip = new DRClip();
      String tmp = attributes.getValue("qid").trim();
      mDRClip.setQid(UUID.fromString(tmp.substring(1, tmp.length() - 1)));
    }
  }
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("clip")) {
      mArrayList.add(mDRClip);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    PropertySetter propertySetter = mPropertySetters.get(mActiveElement);
    if (propertySetter == null)
      return;
    String content = new String(ch, start, length).trim();
    if(content.equals(""))
      return;
    try {
      propertySetter.setProperty(content, mDRClip);
    } catch (Exception e) {
      throw new SAXException(e);
    }
  }

  public ArrayList<DRClip> getList() {
    return mArrayList;
  }

  private interface PropertySetter {
    void setProperty(String content, DRClip drClip) throws Exception;
  }
}
