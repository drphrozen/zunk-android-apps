package dk.znz;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class Program {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		NewsEntry[] newsEntries = DRUpdate.getNewsEntries();
		for (NewsEntry newsEntry : newsEntries) {
			System.out.println(newsEntry.getName());
		}
	}
}