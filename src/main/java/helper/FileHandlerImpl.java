package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import interfaces.FileHandler;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

/**
 * Handles file-related operations
 *
 */

public class FileHandlerImpl implements FileHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String filePath, String filename, List<String> data) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		File f = null;
		try {
			f = new File(filePath + File.separator + filename);
			if (!f.exists()) {
				fw = new FileWriter(f, true);
			} else {
				if (f.delete())
					fw = new FileWriter(f, true);
			}

			bw = new BufferedWriter(fw);
			for (String line : data) {
				bw.write(line);
				bw.newLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (f.exists()) {
					bw.close();
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

	/**
	 * {@inheritDoc}
	 */
	public String extractFilenameFromUrl(String url, int number) {
		return url + "-" + number + ".csv";

	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCsvFileValid(File file, char separator) {
		CSVParser parser = null;
		BufferedReader br = null;
		FileReader reader;

		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			parser = new CSVParserBuilder().withSeparator(separator).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).build();

			csvReader.readAll();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isCsvFileValid(char separator, File file) {
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		String[] columns;
		int headerColumnsNumber = getHeaderColumnsCount(file, separator);
		boolean isValid = true;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			int cpt = 0;
			while((line=br.readLine()) != null) {
				columns = line.split(""+separator);
				cpt++;
				if(headerColumnsNumber != columns.length) {
					isValid = false;
					System.out.println("content = "+line+" row num = "+cpt+" row col count "+columns.length+" headers count = "+headerColumnsNumber+" file name = "+file.getName());
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return isValid;
	}
	
	private int getHeaderColumnsCount(File file, char separator) {
		FileReader fr = null;
		BufferedReader br = null;
		String[] colunms;
		int headerColumnsNumber = 0;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			colunms = br.readLine().split(""+separator);
			headerColumnsNumber = colunms.length;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return headerColumnsNumber;
	}

}
