package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import interfaces.FileHandler;

  
 
  
    public class FileHandlerImpl implements FileHandler{
    	private String fileLocation;
    	private String filename;
    	private List<String> data;
    	
	public void writeToCsvFile( String filename,List<String> data) {
		/*  la localisation du file csv  en parametre String fileLocation,*/
		FileWriter fw = null;
		BufferedWriter bw = null;
		File f = null;
		try {
			f = new File(filename);
			if (!f.exists()) {
				fw = new FileWriter(f, true);
				bw = new BufferedWriter(fw);
				for (String line : data) {
					bw.write(line);
					bw.newLine();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// if(!f.exists()) {
				bw.close();
				fw.close();
				// }
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}

	public String extractFilenameFromUrl(String url) {
		
			// https://fr.wikipedia.org/wiki/Accueil
			String reversedFilename = "";
			String filename = "";
			for (int i = url.length() - 1; i >= 0; i--) {
				if (url.charAt(i) != '/')
					reversedFilename += url.charAt(i);
				else
					break;
			}

			for (int i = reversedFilename.length() - 1; i >= 0; i--)
				filename += reversedFilename.charAt(i);

			return filename;
			
	}

}
