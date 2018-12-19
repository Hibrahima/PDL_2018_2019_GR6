package interfaces;

import java.io.IOException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */


public interface Extractor {
	
	/**
	 * Verifies a given url as a wikipedia url using regular expressions
	 * 
	 * @param url the url to test
	 * @return true if that url is a wikipedia url
	 */
	public boolean isWikipediaUrl(String url);
	
	/**
	 * Ignores some classes supposed to make tables non-pertinent such as navbox
	 * 
	 * @param tableElements the tables whose elements are processed
	 * @return the processed tables
	 */
	public Elements ignoredClasses(Elements tableElements);
	
	/**
	 * Extracts table from a given url
	 * 
	 * @param doc the html representation of that page
	 * @param url the urlof the page
	 * @return the processed tables
	 * @throws IOException if is not possible to get the html-like representation
	 * @throws HttpStatusException if the page does not exist
	 */
	public Elements extractTables(Document doc, String url) throws IOException, HttpStatusException;
	
	/**
	 * Ignores some elements supposed to make tables non-pertinent such as navbox
	 * 
	 * @param tag the tag to process, may ignore a whole table or remove just lines
	 * @param tableElements the tables whose elements are processed
	 * @return the processed tables
	 */
	public Elements ignoredElements(String tag ,Elements tableElements);
	
	/**
	 * Ignore tables with number of rows less than a given number of row
	 * 
	 * @param tableElements the tables whose elements are processed
	 * @param numberOfRows the minimum number of rows for a table not to be ignored
	 * @return the processed tables
	 */
	public Elements ignoreTablesWithLessRows(Elements tableElements, int numberOfRows);
	
}
