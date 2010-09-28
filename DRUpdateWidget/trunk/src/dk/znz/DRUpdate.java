package dk.znz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

final class DRUpdate {
	private static final URI uri = URI.create("http://znz.dk/znk/drupdate.php");
	
	public static NewsEntry[] getNewsEntries(ProgressInfo progressInfo) throws MalformedURLException, IOException, ParseException {
		return getNewsEntries(getJSON(progressInfo));
	}
	
	public static NewsEntry[] getNewsEntries() throws MalformedURLException, IOException, ParseException {
		return getNewsEntries(getJSON());
	}
	private static NewsEntry[] getNewsEntries(String json) throws MalformedURLException, IOException, ParseException {
		if(json == null)
			throw new IOException("No news found, error while parsing!");
		
		JSONArray array = (JSONArray)JSONValue.parseWithException(json);
		NewsEntry[] out = new NewsEntry[array.size()];
		int i = 0;
		for (Object o : array) {
			out[i++] = new NewsEntry((JSONObject)o);
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
		while((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
