package br.com.viniciusmlimarj.socialfx.application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.json.JSONException;

import br.com.viniciusmlimarj.socialfx.instagram.InstagramPost;
import br.com.viniciusmlimarj.socialfx.instagram.InstagramSearch;
import br.com.viniciusmlimarj.socialfx.twitter.Tweet;
import br.com.viniciusmlimarj.socialfx.twitter.TwitterSearch;

/**
 * The principal scene of application.
 * @author Vinicius Mello Lima
 */
public class DashboardController implements Initializable {

	@FXML private ImageView imageViewLogo;

	@FXML private TextField txtSearch;
	@FXML private Button btnSearch;
	@FXML private ListView<String> tweetsListView;

	@FXML private ImageView imageViewInstagram;
	@FXML private Label lblCaptionTextInstagram;
	@FXML private Label lblUserNameInstagram;

	private String search;

	private List<InstagramPost> instagramPosts = new ArrayList<InstagramPost>();
	private TwitterSearch twitterSearch;
	private InstagramSearch instagramSearch;

	private ObservableList<String> tweetsListViewItems = FXCollections.observableArrayList();

	@FXML
	private void search(ActionEvent event) {
		this.search = txtSearch.getText();

		instagramSearch = new InstagramSearch(search.replace("#", ""));
		twitterSearch = new TwitterSearch(search);

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
		Runnable runnableDownloadInstagram = new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (instagramPosts) {
						instagramPosts = instagramSearch.loadNewPosts();
					}
				} catch (JSONException | IOException e) {
					e.printStackTrace();
				}
			}
		};
		ses.scheduleWithFixedDelay(runnableDownloadInstagram, 0, 1, TimeUnit.MINUTES);

		Runnable runnableUpdateTelaInstagram = new Runnable() {
			@Override
			public void run() {
				synchronized (instagramPosts) {
					if (instagramPosts != null && !instagramPosts.isEmpty()) {
						try {
							InstagramPost post = instagramPosts.get(0);
							Platform.runLater(new Runnable() {
								public void run() {
									imageViewInstagram.setImage(new Image(post.getImageUrl()));
									lblCaptionTextInstagram.setText(post.getCaptionText());
									lblUserNameInstagram.setText(post.getUserName());
								}
							});
							instagramPosts.remove(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		};
		ses.scheduleWithFixedDelay(runnableUpdateTelaInstagram, 10, 20, TimeUnit.SECONDS);

		Runnable runnableTwitter = new Runnable() {
			@Override
			public void run() {
				synchronized (tweetsListViewItems) {
					try {
						Tweet tweet = twitterSearch.loadLastTweet();
						tweetsListViewItems.add(tweet.getText());
					} catch (InterruptedException | JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		ses.scheduleWithFixedDelay(runnableTwitter, 0, 10, TimeUnit.SECONDS);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		imageViewLogo.setImage(new Image("/logo.png"));
		tweetsListView.setItems(tweetsListViewItems);
	}
}