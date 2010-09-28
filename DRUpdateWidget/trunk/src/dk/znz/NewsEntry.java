package dk.znz;

import org.json.simple.JSONObject;

public class NewsEntry {
	private final String guid;
	private final String name;
	private final String author;
	private final String authorMail;
	private final String publishedTime;
	private final String description;
	private final String media;
	private final String mediaType;
	private final String image;
	
	private static String getString(Object o) {
		return o != null ? o.toString() : "";
	}
	
	public NewsEntry(JSONObject jo) {
		guid = getString(jo.get("GUID"));
		name = getString(jo.get("Name"));
		author = getString(jo.get("Author"));
		authorMail = getString(jo.get("AuthorMail"));
		publishedTime = getString(jo.get("PublishedTime"));
		description = getString(jo.get("Description"));
		media = "http://www.dr.dk" + getString(jo.get("Media"));
		mediaType = getString(jo.get("MediaType"));
		image = "http://www.dr.dk" + getString(jo.get("Image"));
	}
	
	public String getGuid() {
		return guid;
	}
	public String getName() {
		return name;
	}
	public String getAuthor() {
		return author;
	}
	public String getAuthorMail() {
		return authorMail;
	}
	public String getPublishedTime() {
		return publishedTime;
	}
	public String getDescription() {
		return description;
	}
	public String getMedia() {
		return media;
	}
	public String getMediaType() {
		return mediaType;
	}
	public String getImage() {
		return image;
	}
}
