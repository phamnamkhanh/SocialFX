package br.com.viniciusmlimarj.socialfx.twitter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Instagram Post, with only the properties i need ;)
 * @author Vinicius Mello Lima
 */
public final class Tweet {

	private String text;
	private String userName;
	private String userScreenName;
	private String userProfileImageUrl;

	/**
	 * Constructor which receives a JSONObject with all the properties i need to populate my fields.
	 * @param json The json object with the properties.
	 * @throws JSONException Error in json manipulation.
	 */
	public Tweet(JSONObject json) throws JSONException {
		this.text = json.getString("text");

		JSONObject user = json.getJSONObject("user");
		this.userName = user.getString("name");
		this.userScreenName = user.getString("screen_name");
		this.userProfileImageUrl = user.getString("profile_image_url");
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userScreenName
	 */
	public String getUserScreenName() {
		return userScreenName;
	}

	/**
	 * @param userScreenName the userScreenName to set
	 */
	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	/**
	 * @return the userProfileImageUrl
	 */
	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}

	/**
	 * @param userProfileImageUrl the userProfileImageUrl to set
	 */
	public void setUserProfileImageUrl(String userProfileImageUrl) {
		this.userProfileImageUrl = userProfileImageUrl;
	}
}