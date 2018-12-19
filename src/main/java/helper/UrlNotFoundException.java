package helper;


/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

/**
 * Exception to be thrown when a page does not exist
 */
public class UrlNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UrlNotFoundException(String url) {
		//System.out.println(Constants.CONSOLE_RED_COLOR+"["+url+"] does not exist!");
	}
	
	public UrlNotFoundException() {
		
	}

}
