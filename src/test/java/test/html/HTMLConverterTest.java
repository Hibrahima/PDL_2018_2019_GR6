package test.html;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import helper.Constants;
import helper.FileHandlerImpl;
import interfaces.FileHandler;
import wikipedia.html.HTMLConverter;

public class HTMLConverterTest {
	
	private HTMLConverter htmlConverter;
	private FileHandler fileHandler;
	
	@BeforeEach
	public void setup() {
		 this.htmlConverter = new HTMLConverter();
		 this.fileHandler = new FileHandlerImpl();
	}
	
	@Test
	//@Disabled
	public void testConvetToCSV()
	{
		htmlConverter.convertToCsv(Constants.BASE_WIKIPEDIA_URL, "Comparison_between_Esperanto_and_Ido", Constants.HTML_TEST_OUTPUT_DIR);
		System.out.println();
		//assertTrue(false);
	}
	
	@Test
	@Disabled
	public void testConvertAllToCSV()
	{
		htmlConverter.convertAllToCSV();
		//assertTrue(false);
	}
	
	@Test
	@Disabled
	public void testAreCsvFilesValid() {
		File[] files = null;
		int counter = 0;
		File htmlDirectory = null;
		try {
			htmlDirectory = new File(Constants.HTML_OUTPUT_DIR);
			assertTrue(htmlDirectory.isDirectory());
			files = htmlDirectory.listFiles();
			for(File f : files) {
				counter++;
				System.out.println("current filename = "+f.getName());
				assertTrue(this.fileHandler.isCsvFileValid(f, ','));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println("counter = "+counter);
		
	}
	

}
