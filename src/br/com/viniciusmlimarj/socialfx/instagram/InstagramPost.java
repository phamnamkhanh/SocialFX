package br.com.viniciusmlimarj.socialfx.instagram;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Instagram Post, with only the properties i need ;)
 * @author Vinicius Mello Lima
 */
public final class InstagramPost {

	private String id;
	private String captionText;
	private String userName;
	private String fullName;
	private String profilePictureUrl;
	private int likesCount;
	private String imageUrl;
	private int imageWidth;
	private int imageHeight;

	/**
	 * Constructor which receives a JSONObject with all the properties i need to populate my fields.
	 * @param json The json object with the properties.
	 * @throws JSONException Error in json manipulation.
	 */
	public InstagramPost(JSONObject json) throws JSONException {
		this.id = json.getString("id");
		this.captionText = json.getJSONObject("caption").getString("text");

		JSONObject user = json.getJSONObject("user");
		this.userName = user.getString("username");
		this.fullName = user.getString("full_name");
		this.profilePictureUrl = user.getString("profile_picture");
		this.likesCount = json.getJSONObject("likes").getInt("count");

		JSONObject standardResolutionImage = json.getJSONObject("images").getJSONObject("standard_resolution");
		this.imageUrl = standardResolutionImage.getString("url");
		this.imageWidth = standardResolutionImage.getInt("width");
		this.imageHeight = standardResolutionImage.getInt("height");
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the captionText
	 */
	public String getCaptionText() {
		return captionText;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the profilePictureUrl
	 */
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	/**
	 * @return the likesCount
	 */
	public int getLikesCount() {
		return likesCount;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}
}