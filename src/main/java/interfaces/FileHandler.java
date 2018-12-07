package interfaces;

import java.io.File;
import java.util.List;

public interface FileHandler {
	
	public void write(String filePath, String filename, List<String> data);
	/*  le parametre  a tenir en compte String fileLocation ,*/
	public String extractFilenameFromUrl(String url, int number);
	
	public boolean isCsvFileValid(File file, char separator);
}
