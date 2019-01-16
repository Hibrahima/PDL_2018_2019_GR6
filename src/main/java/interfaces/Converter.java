package interfaces;


/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public interface Converter {

	/**
	 * Processes given tds of a given table in order to avoid conflict with the separator
	 * Replaces characters that conflict with the separator
	 * Formats trs (rows) in a CSV-like style
	 * 
	 * @param tdText the current text of the td (column) to process
	 * @return the processed text of that td
	 */
	public StringBuilder processCurrentTDText(StringBuilder tdText);
	
	/**
	 * Reads a set of urls from the input/data/wikiurls.txt file and converts pertinent tables found on each url
	 * May throw exceptions
	 */
	public void convertAllToCSV();
	
}
