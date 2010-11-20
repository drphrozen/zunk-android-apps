package dk.znz.drupdate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class DRClipParser {
  private final URL       url;
  private final SAXParser saxParser;

  public DRClipParser() throws MalformedURLException, ParserConfigurationException, SAXException {
    url = new URL("http://www.dr.dk/tjenester/iphone/drnyheder/update/update.drxml?feedid=1");
    SAXParserFactory spf = SAXParserFactory.newInstance();
    saxParser = spf.newSAXParser();
  }

  public ArrayList<DRClip> parse() throws IOException, SAXException {
    URLConnection connection = url.openConnection();
    connection.setReadTimeout(3000);

    /* Get the XMLReader of the SAXParser we created. */
    XMLReader xr = saxParser.getXMLReader();

    /* Create a new ContentHandler and apply it to the XML-Reader */
    DRClipHandler drClipHandler = new DRClipHandler();
    xr.setContentHandler(drClipHandler);

    /* Parse the xml-data from our URL. */
    InputSource inputSource = new InputSource(connection.getInputStream());
    inputSource.setEncoding("ISO-8859-1");
    xr.parse(inputSource);
    /* Parsing has finished. */

    return drClipHandler.getList();
  }
}
