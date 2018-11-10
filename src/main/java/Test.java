import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.management.MXBean;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

public class Test {
	
	private HtmlToCsv converter;
	private String url = "https://fr.wikipedia.org/wiki/Java_(langage)";
	
	@BeforeEach
	public void createConverter() {
		converter = new HtmlToCsv();
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("generate CSV files")
	//@Disabled
	public void generateCSVFiles(){
		converter.convertHtmlToCsv(url);
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("test right wikipedia urls")
	public void testRightWikipediaUrls() {
		assertTrue(converter.isWikipediaUrl("https://fr.wikipedia.org/wiki/Java_(langage)"));
		assertTrue(converter.isWikipediaUrl("https://fr.wikipedia.org/wiki/%C3%89quipe_du_Mali_de_football"));
		assertTrue(converter.isWikipediaUrl("https://fr.wikipedia.org/wiki/Universit%C3%A9_Rennes-I"));
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("test wrong wikipedia urls")
	public void testWrongWikipediaUrls() {
		assertFalse(converter.isWikipediaUrl("http://fr.wikipedia.org/wiki/Java_(langage)"));
		assertFalse(converter.isWikipediaUrl("https://fr1.wikipedia.org/wiki/Java_(langage"));
		assertFalse(converter.isWikipediaUrl("https://fr.Wikipedia.org/wiki/Java_(langage"));
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("test generated csv filenames")
	public void testCSVFilenames() {
		assertEquals("Java_(langage)", converter.extractFilenameFromUrl("https://fr.wikipedia.org/wiki/Java_(langage)"));
		assertEquals("Mali", converter.extractFilenameFromUrl("https://fr.wikipedia.org/wiki/Mali"));
		assertEquals("France", converter.extractFilenameFromUrl("https://fr.wikipedia.org/wiki/France"));
		assertNotEquals("france", converter.extractFilenameFromUrl("https://fr.wikipedia.org/wiki/France"));
		assertNotEquals("MalI", converter.extractFilenameFromUrl("https://fr.wikipedia.org/wiki/Mali"));
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("test csv file content")
	//@AfterAll
	@Disabled
	public static void  testCSVFileContent() {
		String[] filenames = {"Java_(langage)_tableau1.csv", "Java_(langage)_tableau2.csv", 
				"Java_(langage)_tableau3.csv", "Java_(langage)_tableau4.csv", "Java_(langage)_tableau5.csv"};
		List<String> currentFileContent = new ArrayList<>();
		for(String currentFilename : filenames) {
			currentFileContent = readFile(currentFilename);
			for(String currentLine : currentFileContent) {
				if(currentFilename.equals("Java_(langage)_tableau1.csv"))
					assertEquals(9, getNumberOfColumnfromLine(currentLine));
				else if(currentFilename.equalsIgnoreCase("Java_(langage)_tableau2.csv"))
					assertEquals(4, getNumberOfColumnfromLine(currentLine));
				else if(currentFilename.equalsIgnoreCase("Java_(langage)_tableau3.csv"))
					assertEquals(2, getNumberOfColumnfromLine(currentLine));
				else if(currentFilename.equalsIgnoreCase("Java_(langage)_tableau4.csv"))
					assertEquals(2, getNumberOfColumnfromLine(currentLine));
				else if(currentFilename.equalsIgnoreCase("Java_(langage)_tableau5.csv"))
					assertEquals(2, getNumberOfColumnfromLine(currentLine));
			}
		}
	}
	
	private static int  getNumberOfColumnfromLine(String line) {
		String[] splittedLine = line.split(",");
		return splittedLine.length;
	}
	
	private static List<String> readFile(String filename) {
		File f =null;
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		List<String> fileContent = new ArrayList<>();
		try {
			f = new File(filename);
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null) {
				fileContent.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				fr.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileContent;
	}

}
