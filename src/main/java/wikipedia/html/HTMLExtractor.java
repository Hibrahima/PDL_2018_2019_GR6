package wikipedia.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper.Constants;
import helper.Constrains;
import helper.Statistics;
import helper.StatisticsImpl;
import interfaces.Extractor;

public class HTMLExtractor implements Extractor {
	private static final String wiki_regex = "^https:\\//[a-z]{2}\\.wikipedia\\.org\\/wiki/.+";
	public static int ignoredTablesCount = 0;
	private Statistics statistics;
	public static Collection<Statistics> statisticsList;
	
	public HTMLExtractor() {
		HTMLExtractor.statisticsList = new ArrayList<>();
	}

	@Override
	public boolean isWikipediaUrl(String url) {
		return Pattern.matches(wiki_regex, url);
	}

	@Override
	public Elements extractTables(Document doc, String url) throws IOException, HttpStatusException {
		Elements tableElements = null;
		statistics = new StatisticsImpl();
		if (!isWikipediaUrl(url)) {
			System.out.println(url + " is not valid");
		} else {
			tableElements = doc.select("table");
			int initialSize = tableElements.size();
			tableElements = convertThsToTds(tableElements);
			tableElements = ignoredElements(url, "div", tableElements);
			tableElements = ignoredElements(url, "ul", tableElements);
			tableElements = ignoredClasses(url, tableElements);
			//tableElements = ignoredElements("img", tableElements);
			tableElements = ignoredElements(url, "p", tableElements);
			//tableElements = ignoredElements(url, "br", tableElements);
			//tableElements = ignoreTablesWithLessRows(url, tableElements, 3);
			int finalSize = tableElements.size();
			statistics.setUrl(url);
			statistics.setIgnoredTablesNumber(initialSize - finalSize );
			statistics.setExtractedTablesNumber(finalSize);
			HTMLExtractor.statisticsList.add(statistics);

		}
		return tableElements;
	}

	@Override
	public Elements ignoredClasses(String url, Elements tableElements) {
		String[] currentTableClasses;
		statistics.setUrl(url);
		for (Element currentTable : tableElements) {
			currentTableClasses = currentTable.className().split(" ");
			for (String cs : currentTableClasses) {
				if (cs.startsWith(Constrains.INFOBOX.getConstrainName())) {
					currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
				}

			}
			if (currentTable.hasClass(Constrains.NAVBOX.getConstrainName())
					&& currentTable.hasClass(Constrains.COLLAPSBLE.getConstrainName())
					&& currentTable.hasClass(Constrains.NOPRINT.getConstrainName())
					&& currentTable.hasClass(Constrains.AUTOCOLLAPSE.getConstrainName())) {
				currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
			}
			

		}

		
		return tableElements.not("."+Constants.GENERIC_CLASS_NAME_TO_REMOVE);
	}

	private Elements convertThsToTds(Elements tableElements) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			for (int i = 0; i < currentTableRowElements.size(); i++) {
				Element currentRow = currentTableRowElements.get(i);
				for (Element ex : currentRow.children()) {
					if (ex.is("th"))
						ex.tagName("td");
				}
			}
		}
		return tableElements;
	}

	@Override
	public Elements ignoredElements(String url, String tag, Elements tableElements) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr"); 
			Elements currentTdTags;
			for (int i = 0; i < currentTableRowElements.size(); i++) {
				Element currentRow = currentTableRowElements.get(i);
				Elements currentRowItems = currentRow.select("td");
				Elements TdInnerTables =  currentRowItems.select("table");
				if(TdInnerTables.size() > 0) {
					TdInnerTables.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
					currentRow.remove();
				}
				
				for (int j = 0; j < currentRowItems.size(); j++) {
					currentTdTags = currentRowItems.get(j).select(tag);
					if (currentRowItems.get(j).hasAttr(Constants.ROW_SPAN_ATTRIBUTE) 
							|| currentRowItems.get(j).hasAttr(Constants.COL_SPAN_ATTRIBUTE)
							|| currentRowItems.get(j).hasClass(Constants.MBOX_IMAGE_CLASS)) {
						currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE); 
					}

					if (currentTdTags.size() > 0) {
						if (tag.equals("p") || tag.equals("br")) {
							currentRowItems.get(j).remove();
						}
						if(tag.equals("div"))
							currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
					}
				}
				
			}
		}
		return tableElements.not("."+Constants.GENERIC_CLASS_NAME_TO_REMOVE);
	}

	@Override
	public Elements ignoreTablesWithLessRows(String url, Elements tableElements, int numberOfRows) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			if (currentTableRowElements.size() < numberOfRows) {
				currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
			}

		}
		return tableElements.not("."+Constants.GENERIC_CLASS_NAME_TO_REMOVE);
	}
	
	
}