import java.io.IOException;
import java.util.List;

import org.jsoup.select.Elements;

public interface Operations {
	
	public boolean isWikipediaUrl(String url);
	public void writeToCsvFile(String fileName, List<String> data);
	public void convertHtmlToCsv(String url);
	public Elements extractTables(String url)throws IOException;
	public String extractFilenameFromUrl(String url);
	

}
