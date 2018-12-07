package wikipedia.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import helper.Constants;
import helper.FileHandlerImpl;
import interfaces.Converter;
import interfaces.FileHandler;

public class HTMLConverter implements Converter {
	private HTMLExtractor extractor;
	private FileHandler filehandler;
	private char separator = ',';

	public HTMLConverter() {
		this.extractor = new HTMLExtractor();
		this.filehandler = new FileHandlerImpl();

	}

	private boolean isNumeric(char character) {
		String numericRegex = "^[0-9]*$";
		String charString = String.valueOf(character);
		return Pattern.matches(numericRegex, charString);
	}

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
			while ((url = br.readLine()) != null) {
				//System.out.println("wikipedia url : " + Constants.BASE_WIKIPEDIA_URL + url);
				convertToCsv(Constants.BASE_WIKIPEDIA_URL, url, Constants.HTML_OUTPUT_DIR);
			}
		} catch (Exception e) {
			//System.out.println("-------------------------------------" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void convertToCsv(String baseUrl, String url, String filePath) {
		List<String> data = new ArrayList<>();
		String line = "";
		String filename;
		StringBuilder currentTdText;
		int filenameCounter = 1;
		try {

			Elements tableElements = this.extractor.extractTables(baseUrl + url);
			for (Element currentTable : tableElements) {
				Elements currentTableTrs = currentTable.select("tr");
				for (int i = 0; i < currentTableTrs.size(); i++) {
					Element currentTr = currentTableTrs.get(i);
					Elements currentRowTds = currentTr.select("td");
					for (int j = 0; j < currentRowTds.size(); j++) {
						currentTdText = new StringBuilder(currentRowTds.get(j).text());
						for (int k = 0; k < currentTdText.length(); k++) {
							if (currentTdText.charAt(k) == separator) {
								if (k > 0) {
									if (!isNumeric(currentTdText.charAt(k - 1))) {
										currentTdText.setCharAt(k, ' ');
										//System.out.println("current td text = " + currentTdText);
									} else {
										if ((k + 1) < currentTdText.length()) {
											if (isNumeric(currentTdText.charAt(k + 1))) {
												// System.out.println("current char "+currentTdText.charAt(k-1)+" td
												// text "+currentTdText);
												currentTdText.setCharAt(k, '.');
											} else
												currentTdText.setCharAt(k, ' ');
										}
									}
								}
							}
						}
						if (j == currentRowTds.size() - 1)
							line += currentTdText.toString();
						else
							line += currentTdText.toString() + separator;
					}
					if (line != "") {
						data.add(line);
						line = "";
					}
				}
				filename = this.filehandler.extractFilenameFromUrl(url, filenameCounter);
				this.filehandler.write(filePath, filename, data);
				System.out.println(filename + " has been generated");
				filenameCounter++;
				data.clear();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}