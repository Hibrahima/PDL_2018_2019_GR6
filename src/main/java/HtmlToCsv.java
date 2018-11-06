import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlToCsv implements Operations {

	// https://fr.wikipedia.org/wiki/Accueil

	private static final String wiki_regex = "^https:\\//[a-z]{2}\\.wikipedia\\.org\\/wiki/.+";

	public boolean isWikipediaUrl(String url) {
		return Pattern.matches(wiki_regex, url);
	}

	public void writeToCsvFile(String filename, List<String> data) {
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

	public void convertHtmlToCsv(String url) {
		List<String> data = new ArrayList<>();
		String line = "";
		String filename;
		int filenameCounter = 1;
		try {
			if (!isWikipediaUrl(url))
				System.out.println("Url non valide!");
			else {
				Elements tableElements = extractTables(url);
				for (Element currentTable : tableElements) {
					Elements tableHeaderEles = currentTable.select("tbody tr th");
					// System.out.println("-----headers--------------- data size : "+data.size());
					for (int i = 0; i < tableHeaderEles.size(); i++) {
						// System.out.println(tableHeaderEles.get(i).text() + ",");
						if (i == tableHeaderEles.size() - 1)
							line += tableHeaderEles.get(i).text();
						else
							line += tableHeaderEles.get(i).text() + ",";
					}
					if (line != "") {
						data.add(line);
						line = "";
					}

					Elements currentTableRowElements = currentTable.select(":not(thead) tr");
					for (int i = 0; i < currentTableRowElements.size(); i++) {
						Element currentRow = currentTableRowElements.get(i);
						Elements currentRowItems = currentRow.select("td");
						for (int j = 0; j < currentRowItems.size(); j++) {
							if (j == currentRowItems.size() - 1)
								line += currentRowItems.get(j).text();
							else
								line += currentRowItems.get(j).text() + ",";
						}
						if (line != "") {
							data.add(line);
							line = "";
						}
					}
					filename = extractFilenameFromUrl(url) + "_tableau" + filenameCounter + ".csv";
					writeToCsvFile(filename, data);
					filenameCounter++;
					data.clear();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Elements extractTables(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements tableElements = doc.select("table").not(".infobox_v2");
		return tableElements;
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
