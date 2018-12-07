package interfaces;

import java.io.IOException;

import org.jsoup.select.Elements;

public interface Extractor {
	public boolean isWikipediaUrl(String url);
	public Elements ignoredClasses(String url, Elements tableElements);
	public Elements extractTables(String url) throws IOException;
	public Elements ignoredElements(String url, String tag ,Elements tableElements);
	
}
