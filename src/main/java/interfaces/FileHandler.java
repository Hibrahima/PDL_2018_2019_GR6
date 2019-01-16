package interfaces;

import java.io.File;
import java.util.List;


/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public interface FileHandler {
	
	/**
	 * Writes text to files line by line
	 * 
	 * @param filePath the path to which the generated file is stored
	 * @param filename the name of that file
	 * @param data the text to be written
	 */
	public void write(String filePath, String filename, List<String> data);
	
	/**
	 * Extracts filenames from url
	 * 
	 * @param url the url of that page
	 * @param number the number of the current table on that page
	 * @return that filename
	 */
	public String extractFilenameFromUrl(String url, int number);
	
	/**
	 * Verifies is a file is well formated with regards to CSV rules
	 * Verifies that a file is a valid CSV file
	 * 
	 * @param file the file to test
	 * @param separator the separator to use for testing
	 * @return true if that file is a valid CSV file
	 */
	public boolean isCsvFileValid(File file, char separator);
	
	/**
	 * Verifies is a file is well formated with regards to CSV rules
	 * Verifies that a file is a valid CSV file
	 * 
	 * @param separator the separator to use for testing
	 * @param file the file to test
	 * @return true if that file is a valid CSV file
	 */
	public boolean isCsvFileValid(char separator, File file);
}
