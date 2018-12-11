package test.html;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import helper.Constants;
import helper.FileHandlerImpl;
import interfaces.FileHandler;
import wikipedia.html.HTMLConverter;

public class HTMLConverterTest {

	private HTMLConverter htmlConverter;
	private FileHandler fileHandler;
	private char separator;

	@BeforeEach
	public void setup() {
		this.htmlConverter = new HTMLConverter();
		this.fileHandler = new FileHandlerImpl();
		separator = ',';
	}

	@Test
	@DisplayName("converts a single url' tables into csv files into our test folders")
	public void testSingleUrlToCSV() throws IOException {
		assertTrue(new File(Constants.HTML_TEST_OUTPUT_DIR).isDirectory());
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_between_Esperanto_and_Ido").get();
		assertDoesNotThrow(() -> htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL,
				"Comparison_between_Esperanto_and_Ido", Constants.HTML_TEST_OUTPUT_DIR));
		System.out.println();

	}

	@Test
	@DisplayName("test Comparison_of_audio_player_software")
	public void testComparison_of_audio_player_software() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_audio_player_software").get();
		htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "Comparison_of_audio_player_software",
				Constants.HTML_TEST_OUTPUT_DIR);
	}

	@Test
	@DisplayName("test converts all files to csv")
	@Tag("robustness")
	public void testConvertAllToCSV() {
		// htmlConverter.convertAllToCSV();
		assertTrue(new File(Constants.HTML_OUTPUT_DIR).isDirectory());
		assertDoesNotThrow(() -> htmlConverter.convertAllToCSV());
	}

	
	@Test
	@DisplayName("test validy of all csv files")
	@Tag("robustness")
	public void testAreCsvFilesValid() {
		File[] files = null;
		int counter = 0;
		File htmlDirectory = null;
		try {
			htmlDirectory = new File(Constants.HTML_OUTPUT_DIR);
			assertTrue(htmlDirectory.isDirectory());
			files = htmlDirectory.listFiles();
			for (File f : files) {
				counter++;
				System.out.println("current filename = " + f.getName());
				assertTrue(this.fileHandler.isCsvFileValid(f, ','));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		System.out.println("counter = " + counter);

	}

}
