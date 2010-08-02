package dk.iha.android.services;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XR480RFID {
	private final String ReaderIP;
	private final String QTags = "/cgi_bin/dataProxy?oper=queryTags&rssi=1";
	private final URL url;
	private final SAXParser saxParser;

	public XR480RFID(String readerIP) throws MalformedURLException, ParserConfigurationException, SAXException {
		ReaderIP = readerIP;
		url = new URL("http://" + ReaderIP + QTags);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		saxParser = spf.newSAXParser();
	}

	public ArrayList<XR480Response> parse() throws IOException, SAXException {
		URLConnection connection = url.openConnection();
		connection.setReadTimeout(3000);

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = saxParser.getXMLReader();

		/* Create a new ContentHandler and apply it to the XML-Reader */
		XR480ResponseHandler xr480ResponseHandler = new XR480ResponseHandler();
		xr.setContentHandler(xr480ResponseHandler);

		/* Parse the xml-data from our URL. */
		xr.parse(new InputSource(connection.getInputStream()));
		/* Parsing has finished. */

		return xr480ResponseHandler.getList();
	}
}

class XR480ResponseHandler extends DefaultHandler {

	private final ArrayList<XR480Response> list = new ArrayList<XR480Response>(10);

	@Override
	public void startDocument() throws SAXException {
		getList().clear();
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("Tag")) {
			processTag(attributes);
		}
	}

	private void processTag(Attributes attributes) {
		Long timestamp = null;
		String raw = null;
		String rssi = null;
		try {
			timestamp = Long.parseLong(attributes.getValue("time"), 16);
		} catch (NumberFormatException ex) {
			timestamp = System.currentTimeMillis()/1000;
		}

		raw = attributes.getValue("raw");
		rssi = attributes.getValue("rssi");
		getList().add(new XR480Response(timestamp, raw, rssi));
	}

	public ArrayList<XR480Response> getList() {
		return list;
	}
}