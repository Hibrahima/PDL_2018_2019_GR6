package helper;

import java.io.File;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */


/**
 * Defines general constants for folder names. base urls. ect...
 */
public class Constants {
	
	public static String EN_WIKIPEDIA_API_BASE_URL = "https://en.wikipedia.org/w/api.php?";
	public static String FR_WIKIPEDIA_API_BASE_URL = "https://fr.wikipedia.org/w/api.php?";
	public static String WIKI_URLS_FILE_PATH = "input" + File.separator + "data" + File.separator + "wikiurls.txt";
	public static String EN_BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
	public static String FR_BASE_WIKIPEDIA_URL = "https://fr.wikipedia.org/wiki/";
	public static String HTML_OUTPUT_DIR = "output" + File.separator + "html" + File.separator;
	public static String WIKI_OUTPUT_DIR = "output" + File.separator + "wiki" + File.separator;
	public static String HTML_TEST_OUTPUT_DIR = "output" + File.separator + "test-html" + File.separator;
	public static String WIKI_TEST_OUTPUT_DIR = "output" + File.separator + "test-wiki" + File.separator;
	public static String CONSOLE_WHITE_COLOR = "\033[0;37m";
	public static String CONSOLE_RED_COLOR = "\033[0;31m";
	public static String GENERIC_CLASS_NAME_TO_REMOVE = "not_pertinent";
	public static String ROW_SPAN_ATTRIBUTE = "rowspan";
	public static String COL_SPAN_ATTRIBUTE = "colspan";
	public static String MBOX_IMAGE_CLASS = "mbox-image";

}
