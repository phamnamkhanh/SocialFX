package br.com.viniciusmlimarj.socialfx.instagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Search for Instagram Posts using a key, and a tag.
 * @author Vinicius Mello Lima
 */
public class InstagramSearch {

	private static final String BASE_SERVICE_URL = "https://api.instagram.com/v1/tags/{tag}/media/recent?client_id={client_id}&max_tag_id={maxTagId}";
	private String clientId = "";
	private String maxTagId = "";
	private String tag = "";

	/**
	 * Constructor which receives the client id of the application registered in instagram's website.
	 * @param clientId Client id of the application
	 * @param tag The tag used to filter posts.
	 */
	public InstagramSearch(/*String clientId, */String tag) {
		//this.clientId = clientId;
		this.clientId = "372645f47e2a468fa32103879b2dbad8";
		this.tag = tag;
	}

	/**
	 * Load the posts created after the last search.
	 * @return A list with the instagram posts.
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public List<InstagramPost> loadNewPosts() throws JSONException, MalformedURLException, IOException {
		JSONObject jsonObject = new JSONObject(loadNewPostsFromService());
		JSONArray jsonArray = jsonObject.getJSONArray("data");

		String nextMaxId = jsonObject.getJSONObject("pagination").optString("next_max_id");
		if (nextMaxId != "") {
			maxTagId = nextMaxId;
		}
		

		List<InstagramPost> posts = new ArrayList<InstagramPost>();
		for (int i=0; i<jsonArray.length(); i++) {
			posts.add(new InstagramPost(jsonArray.getJSONObject(i)));
		}
		return posts;
	}

	private String loadNewPostsFromService() throws MalformedURLException, IOException {
		String url = BASE_SERVICE_URL.replace("{tag}", tag).replace("{client_id}", clientId).replace("{maxTagId}", maxTagId);
		System.out.println(url);
		System.out.println("\n\n");

		InputStream is = null;
		try {
			is = new URL(url).openStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				  sb.append((char) cp);
			}

			return sb.toString();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}
