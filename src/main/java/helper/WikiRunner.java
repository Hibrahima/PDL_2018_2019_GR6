package helper;

import java.io.IOException;
import org.eclipse.mylyn.wikitext.mediawiki.MediaWikiLanguage;
import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WikiRunner {

	public static Document getDocument(String languageVariant, String pageTitle)
			throws IOException, UrlNotFoundException {
		String wikitext = ApiCaller.getWikitext(ApiCaller.getResponse(languageVariant, pageTitle));
		String html = null;
		Document doc = null;
		if (wikitext != null) {
			try {

				MarkupParser markupParser = new MarkupParser();
				markupParser.setMarkupLanguage(new MediaWikiLanguage());
				html = markupParser.parseToHtml(wikitext);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			doc = Jsoup.parse(html);
		}

		return doc;

	}

}
