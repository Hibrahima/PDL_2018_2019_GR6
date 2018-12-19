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

/**
 * 
 * @author Ibrahima HAIDARA
 * @author Mariam Coulibaly
 * @author Mahamadou Sylla
 * @author Abdoul Hamide Ba
 *
 */

/**
 * 
 * Makes Http GET requests in order to get response from mediawiki server
 */
public class ApiCaller {
	
	/**
	 * Encodes a value. i.e for utf-8 url enconding especially parameters
	 * 
	 * @param value the value to encode
	 * @return the encoded value
	 * @throws UnsupportedEncodingException if the encoding method is not supported
	 */
	private static String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}

	/**
	 * Gets the response from mediawiki api
	 * Returns null if no response is provided by the api
	 * 
	 * @param languageVariant the language to use to make api calls, depends on base wikipedia urls ( en or fr)
	 * @param pageTitle the title of the page as stated on wikipedia
	 * @return the response from the api or null
	 * @throws IOException if the response is not readable
	 * @throws UrlNotFoundException if there is an error while getting the response (http response code != 200)
	 */
	private static String getResponse(String languageVariant, String pageTitle) throws IOException, UrlNotFoundException {
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
	
	/**
	 * Extracts the wikitext corresponding to the last revision of a given wikipedia page from the api response
	 * 
	 * @param languageVariant the language variant to use for api calls
	 * @param pageTitle the title of the page whose wikitext is extracted 
	 * @return the extracted wikitext or null if there is no wiktext, meaning the page does not exist
	 * @throws UrlNotFoundException if the page does not exist
	 * @throws IOException if the response from the api is not readable
	 */
	public static String extractWikitextFromApiResponse(String languageVariant, String pageTitle) throws UrlNotFoundException, IOException {
		String apiResponse = ApiCaller.getResponse(languageVariant, pageTitle);
		String wikitext = null;
		try {
			if(apiResponse != null) {
				JSONObject obj = new JSONObject(apiResponse);
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
