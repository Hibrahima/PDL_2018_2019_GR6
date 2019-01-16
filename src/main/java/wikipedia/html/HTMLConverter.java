package wikipedia.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import helper.Constants;
import helper.FileHandlerImpl;
import interfaces.Converter;
import interfaces.FileHandler;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public class HTMLConverter implements Converter {
	private HTMLExtractor extractor;
	private FileHandler filehandler;
	private char separator = ',';

	public HTMLConverter() {
		this.extractor = new HTMLExtractor();
		this.filehandler = new FileHandlerImpl();

	}

	/**
	 * Checks whether or not a character is a number Used to process td text
	 * 
	 * @param character the character to check
	 * @return true if that charcacter is a number
	 */
	private boolean isNumeric(char character) {
		String numericRegex = "^[0-9]*$";
		String charString = String.valueOf(character);
		return Pattern.matches(numericRegex, charString);
	}

	/**
	 * Checks whether or not a url exists May throw HttpStatusException
	 * 
	 * @param url the url to check
	 * @return true if the url exists
	 * @throws IOException if is not possible to connect to that url
	 */
	private boolean doesUrlExist(String url) throws IOException {
		try {
			Jsoup.connect(url).get();
			return true;
		} catch (HttpStatusException e) {
			System.out.println(Constants.CONSOLE_RED_COLOR + "[" + url + "] does not exist!");
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void convertAllToCSV() {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			file = new File(Constants.WIKI_URLS_FILE_PATH);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String url = null;
			int number = 0;
			while ((url = br.readLine()) != null) {
				if (!doesUrlExist(Constants.EN_BASE_WIKIPEDIA_URL + url))
					continue;

				Document doc = Jsoup.connect(Constants.EN_BASE_WIKIPEDIA_URL + url).get();
				convertToCsv(doc, Constants.EN_BASE_WIKIPEDIA_URL, url, Constants.HTML_OUTPUT_DIR);
			}
			System.out.println("CSV serialization finished, " + number + " urls have been tested");
		} catch (Exception e) {

		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
			}
		}

	}

	/**
	 * Converts tables of a given url into a CSV-like file
	 * 
	 * @param doc       the html representation of that page
	 * @param baseUrl   the base url to use to make api calls
	 * @param pageTitle the title of the page
	 * @param filePath  the path for the files to be stored in
	 * @throws HttpStatusException if the page does not exist
	 */
	public void convertToCsv(Document doc, String baseUrl, String pageTitle, String filePath)
			throws HttpStatusException {
		List<String> data = new ArrayList<>();
		String line = "";
		String filename;
		StringBuilder currentTdText;
		int filenameCounter = 1;
		try {
			Elements tableElements = this.extractor.extractTables(doc, baseUrl + pageTitle);
			if (tableElements == null)
				System.out.println("Oups, smething wrong happened");
			else {
				for (Element currentTable : tableElements) {
					Elements currentTableTrs = currentTable.select("tr");
					for (int i = 0; i < currentTableTrs.size(); i++) {
						Element currentTr = currentTableTrs.get(i);
						Elements currentRowTds = currentTr.select("td");
						Element currentTd;
						for (int j = 0; j < currentRowTds.size(); j++) {
							currentTd = currentRowTds.get(j);
							currentTdText = new StringBuilder(currentTd.text());
							currentTdText = processCurrentTDText(currentTdText);
							if (j == currentRowTds.size() - 1)
								line += currentTdText.toString();
							else
								line += currentTdText.toString() + separator;
						}
						if (line != "") {
							line = processLine(line);
							data.add(line);
							line = "";
						}
					}
					filename = this.filehandler.extractFilenameFromUrl(pageTitle, filenameCounter);
					this.filehandler.write(filePath, filename, data);
					System.out.println(Constants.CONSOLE_WHITE_COLOR + filename + " has been generated");
					filenameCounter++;
					data.clear();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String processLine(String line) {
		StringBuilder sb = new StringBuilder(line);
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '\n') 
				sb.setCharAt(i, ' ');
		}
		
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder processCurrentTDText(StringBuilder tdText) {
		for (int k = 0; k < tdText.length(); k++) {
			if (tdText.charAt(k) == separator) {
				if (k > 0) {
					if (!isNumeric(tdText.charAt(k - 1))) {
						tdText.setCharAt(k, ' ');
					} else {
						if ((k + 1) < tdText.length()) {
							if (isNumeric(tdText.charAt(k + 1))) {
								tdText.setCharAt(k, '.');
							} else
								tdText.setCharAt(k, ' ');
						}
					}
				}
				if (k == tdText.length() - 1)
					tdText.setCharAt(k, ' ');
			}
		}

		return tdText;
	}

}