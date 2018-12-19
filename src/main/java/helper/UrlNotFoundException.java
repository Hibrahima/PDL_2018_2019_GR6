package helper;

public class UrlNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UrlNotFoundException(String url) {
		//System.out.println(Constants.CONSOLE_RED_COLOR+"["+url+"] does not exist!");
	}
	
	public UrlNotFoundException() {
		
	}

}
