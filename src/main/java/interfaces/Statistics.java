package interfaces;


/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public interface Statistics {
	
	/**
	 * Gets the url of a given statistics object, getter
	 * @return that url
	 */
	public String getUrl();
	
	/**
	 * Sets the url of a given statistics object, setter
	 * 
	 * Throws NullPointerException if the url is null
	 * @param url the url to set
	 */
	public void setUrl(String url);
	
	/**
	 * Gets the number of ignored tables of a given statistics object, getter
	 * @return that number
	 */
	public int getIgnoredTablesNumber();
	
	/**
	 * Sets the number of ignored tables of a given statistics object, setter
	 * 
	 * Throws NullPointerException if the ignoredTablesNumber is null
	 * @param ignoredTablesNumber the  number of ignored tables to set
	 */
	public void setIgnoredTablesNumber(int ignoredTablesNumber);
	
	/**
	 * Gets the number of extracted tables of a given statistics object, getter
	 * @return that number
	 */
	public int getExtractedTablesNumber();
	
	/**
	 * Sets the number of extracted tables of a given statistics object, setter
	 * 
	 * Throws NullPointerException if the extractedTablesNumber is null
	 * @param extractedTablesNumber the  number of extracted tables to set
	 */
	public void setExtractedTablesNumber(int extractedTablesNumber);

}
