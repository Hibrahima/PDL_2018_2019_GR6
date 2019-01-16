package html;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import helper.Constants;
import helper.FileHandlerImpl;
import helper.Utils;
import interfaces.FileHandler;
import interfaces.Statistics;
import wikipedia.html.HTMLConverter;
import wikipedia.html.HTMLExtractor;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public class HTMLConverterTest {
	
	private HTMLConverter htmlConverter;
	private static FileHandler fileHandler;
	private static char separator;
	
	@BeforeEach
	public void setup() {
		 this.htmlConverter = new HTMLConverter();
		 fileHandler = new FileHandlerImpl();
		 separator = ',';
	}
	
	
	/* DISABLED [ JUST FOR LOCAL TRST PURPOSES ]
	@Test
	@DisplayName("converts a single url' tables into csv files into our test folders")
	@Disabled
	public void testSingleUrlToCSV() throws IOException
	{
		File dir = new File(Constants.HTML_TEST_OUTPUT_DIR);
		assertTrue(dir.isDirectory());
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_neurofeedback_software").get();
		assertDoesNotThrow(() -> htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "Comparison_of_neurofeedback_software", Constants.HTML_TEST_OUTPUT_DIR));
		
		File[] files = dir.listFiles();
		for(File f : files) {
			fileHandler.isCsvFileValid(separator, f);
		}
	}
	
	@Test
	@DisplayName("test Comparison_of_audio_player_software")
	@Disabled
	public void testComparison_of_audio_player_software() throws IOException {
		Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + "Comparison_of_audio_player_software").get();
		htmlConverter.convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, "Comparison_of_audio_player_software", Constants.HTML_TEST_OUTPUT_DIR);
	}
	*/
	
	@Test
	@DisplayName("test converts all files to csv")
	@Tag("robustness")
	public void testConvertAllToCSV()
	{
		try {
		assertTrue(new File(Constants.HTML_OUTPUT_DIR).isDirectory());
		 assertDoesNotThrow(()-> htmlConverter.convertAllToCSV());
		
		System.out.println("------------------ Statistics for Html Converter --------------");
		int totalExtacted = 0;
		for(Statistics s : HTMLExtractor.statisticsList) {
			totalExtacted += s.getExtractedTablesNumber();
		}
		System.out.println("Number of extracted pertinent tables : "+totalExtacted);
		Utils.displayInfo(HTMLExtractor.statisticsList, "HTML Extractor");
		}catch (Exception e) {
			e.printStackTrace();
		}
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
		assertEquals("some text"+" ", result.toString());
		
	}
	
	
	/* TO BE IMPROVED
	@Test
	@AfterAll
	@DisplayName("test validity of all csv files")
	@Tag("robustness")
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
				System.out.println("CSV validity of current file, filename : "+f.getName()+" is valid : "+fileHandler.isCsvFileValid(f, separator));
				assertTrue(fileHandler.isCsvFileValid(f, separator));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println("total number of files tested : "+counter);
		
	}
	*/
	
	
	
	@Test
	@AfterAll
	@DisplayName("test validity of all csv files")
	@Tag("robustness")
	public static void testAreCsvFilesValid2() {
		File[] files = null;
		int counter = 0;
		File htmlDirectory = null;
		try {
			htmlDirectory = new File(Constants.HTML_OUTPUT_DIR);
			assertTrue(htmlDirectory.isDirectory());
			files = htmlDirectory.listFiles();
			for(File f : files) {
				counter++;
				fileHandler.isCsvFileValid(separator, f);
				System.out.println(f.getName()+" is valid : "+fileHandler.isCsvFileValid(separator, f));
				assertTrue(fileHandler.isCsvFileValid(separator, f));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println("total number of files tested : "+counter);
		
	}
	
	
}
