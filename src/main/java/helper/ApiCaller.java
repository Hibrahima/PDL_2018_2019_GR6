package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCaller {
	
	private static String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}
	


	public static String getResponse(String languageVariant, String pageTitle) throws IOException, UrlNotFoundException {
		String apiUrl = "";
		if(languageVariant.equals("en"))
		 apiUrl = Constants.EN_WIKIPEDIA_API_BASE_URL+"action=query&prop=revisions|wikitext&rvprop=content&format=json&formatversion=2&titles="+encodeValue(pageTitle);
		else if(languageVariant.equals("fr"))
			apiUrl = Constants.FR_WIKIPEDIA_API_BASE_URL+"action=query&prop=revisions|wikitext&rvprop=content&format=json&formatversion=2&titles="+pageTitle;
		StringBuffer response = null;
		URL urlObject = new URL(apiUrl);
				
		HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode(); 
		
		
		if(responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}
		else {
			if(languageVariant.equals("fr"))
				throw new UrlNotFoundException(pageTitle);
			else if(languageVariant.equals("en"))
				throw new UrlNotFoundException(pageTitle);
		}
		
		return response.toString();
		
	}
	
	public static String getWikitext(String response) throws UrlNotFoundException {
		String wikitext = null;
		try {
			if(response != null) {
				JSONObject obj = new JSONObject(response);
				JSONObject query = obj.getJSONObject("query");
				if(query.has("pages")) {
					JSONArray pages = query.getJSONArray("pages");
					if(!pages.isEmpty() && pages.getJSONObject(0).has("revisions")) {
						JSONArray revisions = pages.getJSONObject(0).getJSONArray("revisions");
						if(revisions.getJSONObject(0).has("content"))
							wikitext = revisions.getJSONObject(0).getString("content");
					}
				}
				
			}
			
			if(wikitext == null)
				throw new UrlNotFoundException();
		}catch (UrlNotFoundException e) {
			throw new UrlNotFoundException();
		}
		
		return wikitext;
	}


}
