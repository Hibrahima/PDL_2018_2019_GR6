package wikipedia.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper.FileHandlerImpl;
import interfaces.Converter;
import interfaces.FileHandler;



public class HTMLConverter implements Converter{
	private HTMLExtractor extractor;
	private FileHandler filehandler;
	private char separator;
	
	public HTMLConverter(){
		this.extractor = new HTMLExtractor();
		this.filehandler = new FileHandlerImpl();
		this.extractor = new HTMLExtractor();
		
		
		}
	@Override
	public void convertHtmlToCsv(String url) {
		List<String> data = new ArrayList<>();
		String line = "";
		String filename;
		int filenameCounter = 1;
		try {
			if (!this.extractor.isWikipediaUrl(url))
				System.out.println("Url non valide!");
			else {
				Elements tableElements = this.extractor.extractTables(url);
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
					filename = this.filehandler.extractFilenameFromUrl(url) + "_tableau" + filenameCounter + ".csv";
					this.filehandler.writeToCsvFile(filename, data);
					filenameCounter++;
					data.clear();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}