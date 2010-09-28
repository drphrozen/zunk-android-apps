package dk.znz;
import java.io.IOException;

import org.json.simple.parser.ParseException;


public class Program {

	public static void main(String[] args) throws IOException, ParseException {
		NewsEntry[] newsEntries = DRUpdate.getNewsEntries();
		for (NewsEntry newsEntry : newsEntries) {
			System.out.println(newsEntry.getName());
		}
	}
}