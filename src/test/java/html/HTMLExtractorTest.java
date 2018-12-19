package html;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

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
	
	@Test
	@DisplayName("Comparison_of_TLS_implementations")
	public void testComparison_of_TLS_implementations() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_TLS_implementations").get();
		Elements tables = extractor.extractTables(doc, Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_TLS_implementations");
		assertEquals(18, tables.size());
	}
	
	@Test
	@DisplayName("Python_(langage)")
	public void testComparison_of_PythonLangage() throws IOException {
		Document doc = Jsoup.connect(Constants.FR_BASE_WIKIPEDIA_URL + "Python_(langage)").get();
		Elements tables = extractor.extractTables(doc, Constants.FR_BASE_WIKIPEDIA_URL + "Python_(langage)");
		assertEquals(0, tables.size());
	}
	
	
	@Test
	@DisplayName("Comparison_of_Exchange_ActiveSync_clients")
	@Tag("robustness")
	public void testComparison_of_Exchange_ActiveSync_clients() throws IOException {
		assertThrows(HttpStatusException.class, () -> Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_Exchange_ActiveSync_clients").get());
	}
	
	@Test
	@DisplayName("Comparison_of_IPv6_application_support")
	@Tag("robustness")
	public void testComparison_of_IPv6_application_support() throws IOException {
		assertThrows(HttpStatusException.class, () -> Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_IPv6_application_support").get());
	}
	
	
	@Test
	@DisplayName("Comparison_of_audio_player_software)")
	@Tag("robustness")
	public void testComparison_of_audio_player_software() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_audio_player_software").get();
		Elements tables = extractor.extractTables(doc, Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_audio_player_software");
		assertEquals(9, tables.size());
	}
}
