package wikipedia.html;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import interfaces.Extractor;

public class HTMLExtractor implements Extractor{
	private static final String wiki_regex = "^https:\\//[a-z]{2}\\.wikipedia\\.org\\/wiki/.+";
	@Override
	public boolean isWikipediaUrl(String url) {
		return Pattern.matches(wiki_regex, url);
	}

	@Override
	public Elements extractTables(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements tableElements = doc.select("table").not(".infobox_v2");
		return tableElements;
	}

}
