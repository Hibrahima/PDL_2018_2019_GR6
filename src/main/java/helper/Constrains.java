package helper;

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

/**
 * Defines some constrains that are used in the extractors
 */
public enum Constrains {
	
	INFOBOX("infobox"),
	NAVBOX("navbox"),
	COLLAPSBLE("collapsible"),
	AUTOCOLLAPSE("autocollapse"),
	NOPRINT("noprint");
	
	private String constrainName;
	
	private Constrains(String name) {
		this.constrainName = name;
	}

	public String getConstrainName() {
		return constrainName;
	}

	public void setConstrainName(String constrainName) {
		this.constrainName = constrainName;
	}
	
	

}
