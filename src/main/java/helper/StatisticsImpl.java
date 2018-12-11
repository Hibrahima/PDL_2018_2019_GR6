package helper;

import java.util.Objects;

public class StatisticsImpl implements Statistics {

	private String url;
	private int ignoredTablesNumber;
	private int extractedTablesNumber;

	public StatisticsImpl(String url, int ignoredTablesNumber, int extractedTablesNumber) {
		super();
		this.url = url;
		this.ignoredTablesNumber = ignoredTablesNumber;
		this.extractedTablesNumber = extractedTablesNumber;
	}
	
	public StatisticsImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

	@Override
	public int getIgnoredTablesNumber() {
		// TODO Auto-generated method stub
		return this.ignoredTablesNumber;
	}

	@Override
	public int getExtractedTablesNumber() {
		// TODO Auto-generated method stub
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
