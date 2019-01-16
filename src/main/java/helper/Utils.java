package helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import interfaces.Statistics;

public class Utils {
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static void displayInfo(Collection<Statistics> statistics, String extractorName) {
		TablePrinter generalPrinter = new TablePrinter();
		TablePrinter summaryPriter = new TablePrinter();
        //st.setRightAlign(true);//if true then cell text is right aligned
		generalPrinter.setShowVerticalLines(true); //if false (default) then no vertical lines are shown
		generalPrinter.setHeaders("Url", "Igored", "Extracted", "Total");//optional - if not used then there will be no header and horizontal lines
		
		int total= 0;
		int totalIgnored = 0;
		int totalExtracted = 0;
		for(Statistics s : statistics) {
			int totalPerUrl = s.getExtractedTablesNumber()+s.getIgnoredTablesNumber();
			total += totalPerUrl;
			totalIgnored += s.getIgnoredTablesNumber();
			totalExtracted += s.getExtractedTablesNumber();
			generalPrinter.addRow(s.getUrl(), ""+s.getIgnoredTablesNumber(), ""+s.getExtractedTablesNumber(),""+totalPerUrl);
		}
		generalPrinter.print();
		
		double ignoredPertcent = (100.0 * totalIgnored) / total;
		ignoredPertcent = round(ignoredPertcent, 2);
		double extractedPercent = (100.0 * totalExtracted) / total;
		extractedPercent = round(extractedPercent, 2);
		System.out.println("--------- Summary -----------");
		summaryPriter.setHeaders("Total", "Total Extracted - %", "Total Ignored - %", "Extractor");
		summaryPriter.setShowVerticalLines(true);
		summaryPriter.addRow(""+total, ""+totalExtracted+" - "+extractedPercent+" % ", 
				""+totalIgnored+" - "+ignoredPertcent+" % ", extractorName);
		summaryPriter.print();
	}


}
