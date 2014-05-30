package br.com.viniciusmlimarj.socialfx.twitter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterSearch {

	private Client hosebirdClient;
	private BlockingQueue<String> msgQueue;

	/**
	 * Constructor which receive the search params.
	 * @param hashtags a list of hashtags.
	 */
	public TwitterSearch(String ... hashtags) {
		msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		List<Long> followings = Lists.newArrayList(1234L, 566788L);
		List<String> terms = Lists.newArrayList(hashtags);
		hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(terms);

		Authentication hosebirdAuth = new OAuth1("j2HFhyxzofc2pKC113pwJOvr9", "mKXZgBwfZvsZMc34HpKzcFtRkM1DZalu8JyzeB9Wdt2cnX2iA3", "132222386-EAS8XK68cFxOCDhTsxrGdS6cnTrIvGyIorEaZ4fs", "Vrtb1uFYMCMUHTZ7J7dOvzCDPlU0QxmKKNycom6gZeId5");

		ClientBuilder builder = new ClientBuilder()
								.name("Hosebird-Client-01")
								.hosts(hosebirdHosts)
								.authentication(hosebirdAuth)
								.endpoint(hosebirdEndpoint)
								.processor(new StringDelimitedProcessor(msgQueue))
								.eventMessageQueue(eventQueue);

		hosebirdClient = builder.build();
		hosebirdClient.connect();
	}

	/**
	 * Load the tweets created after the last search.
	 * @return A list with the tweets
	 * @throws InterruptedException
	 * @throws JSONException 
	 */
	public Tweet loadLastTweet() throws InterruptedException, JSONException {
		Tweet tweet = null;
		if (!hosebirdClient.isDone()) {
			String msg = msgQueue.take();
			JSONObject json = new JSONObject(msg);
			tweet = new Tweet(json);
		}
		return tweet;
	}
}
