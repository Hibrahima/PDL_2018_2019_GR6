package wiki;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import helper.Constants;
import helper.FileHandlerImpl;
import helper.UrlNotFoundException;
import helper.Utils;
import helper.WikiRunner;
import interfaces.FileHandler;
import interfaces.Statistics;
import wikipedia.wiki.WikiConverter;
import wikipedia.wiki.WikiExtractor;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public class WikiConverterTest {
	
	private WikiConverter wikiConverter;
	private static FileHandler fileHandler;
	private static char separator;
	
	@BeforeEach
	public void setup() {
		 this.wikiConverter = new WikiConverter();
		 fileHandler = new FileHandlerImpl();
		 separator = ',';
	}
	
	/* DISBALED AS WELL [ JUST FOR LOCAL TESTS ]
	@Test
	@DisplayName("converts a single url' tables into csv files into our test folders")
	@Disabled
	public void testSingleUrlToCSV() throws IOException, UrlNotFoundException
	{
		assertTrue(new File(Constants.WIKI_TEST_OUTPUT_DIR).isDirectory());
		Document doc =   WikiRunner.getDocument("en","List_of_Nvidia_graphics_processing_units"); 
		assertDoesNotThrow(() -> wikiConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "List_of_Nvidia_graphics_processing_units", Constants.WIKI_TEST_OUTPUT_DIR));
		
	}
	
	
	@Test
	@DisplayName("test Comparison_of_audio_player_software")
	@Disabled
	public void testComparison_of_Linux_distributions() throws IOException, UrlNotFoundException{
		Document doc = WikiRunner.getDocument("en", " Comparison_of_Linux_distributions");
		wikiConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, " Comparison_of_Linux_distributions", Constants.WIKI_TEST_OUTPUT_DIR);
	}
	*/
	
	@Test
	@DisplayName("test converts all files to csv")
	@Tag("robustness")
	public void testConvertAllToCSV()
	{
		try {
			assertTrue(new File(Constants.WIKI_OUTPUT_DIR).isDirectory());
			assertDoesNotThrow(() -> wikiConverter.convertAllToCSV());
			
			System.out.println("------------------ Statistics for Wiki Converter --------------");
			int totalExtacted = 0;
			for(Statistics s : WikiExtractor.statisticsList) {
				totalExtacted += s.getExtractedTablesNumber();
			}
			System.out.println("Number of extracted pertinent tables : "+totalExtacted);
			Utils.displayInfo(WikiExtractor.statisticsList, "WIKI Extractor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	@DisplayName("test process td text with separator and number")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAndNumber() {
		StringBuilder  result = wikiConverter.processCurrentTDText(new StringBuilder("154"+separator+"8"));
		assertEquals("154.8", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("247"+separator+"a"));
		assertEquals("247 a", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("0"+separator+"z"));
		assertEquals("0 z", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("-45"+separator+"abc def"));
		assertEquals("-45 abc def", result.toString());
	}
	
	@Test
	@DisplayName("test process td text with separator and caracters")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAndCaracters() {
		StringBuilder  result = wikiConverter.processCurrentTDText(new StringBuilder("true"+separator+"false"));
		assertEquals("true false", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("true"+separator+"36"));
		assertEquals("true 36", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("life is complated"+separator+"-17"));
		assertEquals("life is complated -17", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("-45"+separator+"abc def"));
		assertEquals("-45 abc def", result.toString());
	}
	
	@Test
	@DisplayName("test process td text with separator at end and beginning")
	@Tag("robustness")
	public void testPrcocessTdTextWithSeparatorAtEndAndBeginniing() {
		StringBuilder  result = wikiConverter.processCurrentTDText(new StringBuilder(separator+"some text"));
		assertEquals(separator+"some text", result.toString());
		
		result = wikiConverter.processCurrentTDText(new StringBuilder("some text"+separator));
		assertEquals("some text"+" ", result.toString());
		
	}
	
	
	// TO BE IMPROVED
	@Test
	@AfterAll
	@DisplayName("test validity of all csv files")
	@Tag("robustness")
	public static void testAreCsvFilesValid() {
		File[] files = null;
		int counter = 0;
		File wikiDirectory = null;
		try {
			wikiDirectory = new File(Constants.WIKI_OUTPUT_DIR);
			assertTrue(wikiDirectory.isDirectory());
			files = wikiDirectory.listFiles();
			for(File f : files) {
				counter++;
				System.out.println("CSV validity of current file, filename : "+f.getName()+" is valid : "+fileHandler.isCsvFileValid(f, separator));
				assertTrue(fileHandler.isCsvFileValid(f, separator));
			}
		} catch (Exception e) {
		}
		
		System.out.println("total number of files tested : "+counter);
		
	}	

	
	
}
