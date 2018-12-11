package interfaces;

import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public interface Extractor {
	public boolean isWikipediaUrl(String url);
	public Elements ignoredClasses(String url, Elements tableElements);
	public Elements extractTables(Document doc, String url) throws IOException, HttpStatusException;
	public Elements ignoredElements(String url, String tag ,Elements tableElements);
	public Elements ignoreTablesWithLessRows(String url, Elements tableElements, int numberOfRows);
	
}
