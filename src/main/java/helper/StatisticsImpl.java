package helper;

import java.util.Objects;

public class StatisticsImpl implements Statistics {

	private String url;
	private int ignoredTablesNumber;
	private int extractedTablesNumber;

	public StatisticsImpl(String url, int ignoredTablesNumber, int extractedTablesNumber) {
		Objects.requireNonNull(url, "url should ne be null");
		Objects.requireNonNull(ignoredTablesNumber, "number of ignored tables should ne be null");
		Objects.requireNonNull(extractedTablesNumber, "number of extracted tables should ne be null");
		this.url = url;
		this.ignoredTablesNumber = ignoredTablesNumber;
		this.extractedTablesNumber = extractedTablesNumber;
	}
	
	public StatisticsImpl() {
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public int getIgnoredTablesNumber() {
		return this.ignoredTablesNumber;
	}

	@Override
	public int getExtractedTablesNumber() {
		return this.extractedTablesNumber;
	}

	@Override
	public void setUrl(String url) {
		Objects.requireNonNull(url, "url cannot be null");
		this.url = url;
	}

	@Override
	public void setIgnoredTablesNumber(int ignoredTablesNumber) {
		Objects.requireNonNull(ignoredTablesNumber, "the number of tables that were ignored  cannot be null");
		this.ignoredTablesNumber = ignoredTablesNumber;
	}

	@Override
	public void setExtractedTablesNumber(int extractedTablesNumber) {
		Objects.requireNonNull(extractedTablesNumber, "the number of tables that were extracted  cannot be null");
		this.extractedTablesNumber  = extractedTablesNumber;
	}
}
