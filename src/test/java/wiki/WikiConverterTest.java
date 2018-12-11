package wiki;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import helper.Constants;
import helper.FileHandlerImpl;
import interfaces.FileHandler;
import wikipedia.html.WikiConverter;

public class WikiConverterTest {
	
	private WikiConverter htmlConverter;
	private static FileHandler fileHandler;
	private static char separator;
	
	@BeforeEach
	public void setup() {
		 this.htmlConverter = new WikiConverter();
		 fileHandler = new FileHandlerImpl();
		 separator = ',';
	}
	
	@Test
	@DisplayName("converts a single url' tables into csv files into our test folders")
	@Disabled
	public void testSingleUrlToCSV() throws IOException
	{
		assertTrue(new File(Constants.HTML_TEST_OUTPUT_DIR).isDirectory());
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_between_Esperanto_and_Ido").get();
		assertDoesNotThrow(() -> htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "Comparison_between_Esperanto_and_Ido", Constants.HTML_TEST_OUTPUT_DIR));
		System.out.println();
		
	}
	
	@Test
	@DisplayName("test Comparison_of_audio_player_software")
	public void testComparison_of_audio_player_software() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_audio_player_software").get();
		htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "Comparison_of_audio_player_software", Constants.HTML_TEST_OUTPUT_DIR);
	}
	
	@Test
	@DisplayName("test converts all files to csv")
	@Tag("robustness")
	public void testConvertAllToCSV()
	{
		assertTrue(new File(Constants.HTML_OUTPUT_DIR).isDirectory());
		assertDoesNotThrow(() -> htmlConverter.convertAllToCSV());
	}
	
	@Test
	@DisplayName("test process td text with separator and number")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAndNumber() {
		StringBuilder  result = htmlConverter.processCurrentTDText(new StringBuilder("154"+separator+"8"));
		assertEquals("154.8", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("247"+separator+"a"));
		assertEquals("247 a", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("0"+separator+"z"));
		assertEquals("0 z", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("-45"+separator+"abc def"));
		assertEquals("-45 abc def", result.toString());
	}
	
	@Test
	@DisplayName("test process td text with separator and caracters")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAndCaracters() {
		StringBuilder  result = htmlConverter.processCurrentTDText(new StringBuilder("true"+separator+"false"));
		assertEquals("true false", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("true"+separator+"36"));
		assertEquals("true 36", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("life is complated"+separator+"-17"));
		assertEquals("life is complated -17", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("-45"+separator+"abc def"));
		assertEquals("-45 abc def", result.toString());
	}
	
	@Test
	@DisplayName("test process td text with separator at end and beginning")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAtEndAndBeginniing() {
		StringBuilder  result = htmlConverter.processCurrentTDText(new StringBuilder(separator+"some text"));
		assertEquals(separator+"some text", result.toString());
		
		result = htmlConverter.processCurrentTDText(new StringBuilder("some text"+separator));
		assertEquals("some text"+separator, result.toString());
		
	}
	
	//@Test
	@AfterAll
	//@DisplayName("test validity of all csv files")
	//@Tag("robustness")
	public static void testAreCsvFilesValid() {
		File[] files = null;
		int counter = 0;
		File htmlDirectory = null;
		try {
			htmlDirectory = new File(Constants.HTML_OUTPUT_DIR);
			assertTrue(htmlDirectory.isDirectory());
			files = htmlDirectory.listFiles();
			for(File f : files) {
				counter++;
				System.out.println("current filename tested : "+f.getName());
				assertTrue(fileHandler.isCsvFileValid(f, separator));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println("counter = "+counter);
		
	}
	

}
