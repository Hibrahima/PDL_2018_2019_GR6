package interfaces;

import java.util.List;

public interface FileHandler {
	
	public void writeToCsvFile( String filename, List<String> data);
	/*  le parametre  a tenir en compte String fileLocation ,*/
	public String extractFilenameFromUrl(String url);
}
