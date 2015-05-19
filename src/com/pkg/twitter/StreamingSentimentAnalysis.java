package com.pkg.twitter;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@ManagedBean(name = "streamingSentiment")
@SessionScoped
public class StreamingSentimentAnalysis {
	private static String CONSUMER_KEY = Authorization.getConsumerKey();
	private static String CONSUMER_KEY_SECRET = Authorization
			.getConsumerKeySecret();
	private static String AUTHORIZATION_TOKEN = Authorization
			.getAuthorizationToken();
	private static String AUTHORIZATION_SECRET = Authorization
			.getAuthorizationSecret();
	public static int iteration = 0;

	public static void getTweets() throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
		cb.setOAuthAccessToken(AUTHORIZATION_TOKEN);
		cb.setOAuthAccessTokenSecret(AUTHORIZATION_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);
		StreamingHeatMap shp = (StreamingHeatMap) session
				.getAttribute("streamingHeatMap");
		try {
			System.out.println("In try");
			Query query = new Query(shp.getKeyword());
			query.setLang("en");
			QueryResult result;
			// Naming the main loop, so that we can exit from nested loop when
			// required
			main_loop: do {
				System.out.println("In do");
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					iteration++;
					System.out.println("In for: Iteration : " + iteration);
					WriteToTweetsFile.write(tweet.getText().replaceAll("\n", " "));
					System.out.println(tweet.getText());
					if (iteration == shp.getNumberOfTweets()) {
						break main_loop;
					}
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			System.out.println(te);
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
	}
	
}