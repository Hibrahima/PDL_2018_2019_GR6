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
import helper.StatisticsImpl;
import interfaces.Extractor;
import interfaces.Statistics;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

public class HTMLExtractor implements Extractor {
	private static final String wiki_regex = "^https:\\//[a-z]{2}\\.wikipedia\\.org\\/wiki/.+";
	public static int ignoredTablesCount = 0;
	private Statistics statistics;
	public static Collection<Statistics> statisticsList;
	
	public HTMLExtractor() {
		HTMLExtractor.statisticsList = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWikipediaUrl(String url) {
		return Pattern.matches(wiki_regex, url);
	}

	/**
	 * {@inheritDoc}
	 */
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
			tableElements = ignoredElements("div", tableElements);
			tableElements = ignoredElements("ul", tableElements);
			tableElements = ignoredClasses(tableElements);
			//tableElements = ignoredElements("img", tableElements);
			tableElements = ignoredElements("p", tableElements);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Elements ignoredClasses(Elements tableElements) {
		String[] currentTableClasses;
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

	/**
	 * Converts ths to tds
	 * 
	 * @param tableElements the tables whose ths are transformed to tds
	 * @return the processed tables
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Elements ignoredElements(String tag, Elements tableElements) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Elements ignoreTablesWithLessRows(Elements tableElements, int numberOfRows) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			if (currentTableRowElements.size() < numberOfRows) {
				currentTable.addClass(Constants.GENERIC_CLASS_NAME_TO_REMOVE);
			}

		}
		return tableElements.not("."+Constants.GENERIC_CLASS_NAME_TO_REMOVE);
	}
	
	
}