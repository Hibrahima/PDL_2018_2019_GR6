package test.html;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import helper.Constants;
import interfaces.Extractor;
import wikipedia.html.HTMLExtractor;

public class HTMLExtractorTest {
	
	private Extractor extractor;
	
	@BeforeEach
	public void setup() {
		extractor = new HTMLExtractor();
	}
	
	@Test
	@DisplayName("Comparison_of_SSH_clients")
	public void testComparison_of_SSH_clients() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_SSH_clients").get();
		Elements tables = extractor.extractTables(doc, Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_SSH_clients");
		assertEquals(3, tables.size());
	}
	
	
}
