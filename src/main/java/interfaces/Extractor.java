package interfaces;

import java.io.IOException;

import org.jsoup.select.Elements;

public interface Extractor {
	public boolean isWikipediaUrl(String url);
	public Elements extractTables(String url) throws IOException;
}
