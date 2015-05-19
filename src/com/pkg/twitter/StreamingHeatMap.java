package com.pkg.twitter;

import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.pkg.mapdata.ReadMapRedOutput;
import com.pkg.wordcount.LaunchWordCount;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

@ManagedBean(name = "streamingHeatMap")
@SessionScoped
public class StreamingHeatMap {
	private static String CONSUMER_KEY = Authorization.getConsumerKey();
	private static String CONSUMER_KEY_SECRET = Authorization
			.getConsumerKeySecret();
	private static String AUTHORIZATION_TOKEN = Authorization
			.getAuthorizationToken();
	private static String AUTHORIZATION_SECRET = Authorization
			.getAuthorizationSecret();
	public static int iteration = 0;
	public static volatile boolean streamStarted = false;
	public static volatile boolean streamEnded = false;
	private static int numberOfTweets;

	private static String keyword;

	public String entry() throws Exception {
		System.out.println("Start");
		System.out.println("Keyword : " + keyword);
		System.out.println("number of tweets: " + numberOfTweets);
		// Read the list of states and save them in memory
		ReadStates rs = new ReadStates();
		// Stream tweets
		iteration = 0;

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
		cb.setOAuthAccessToken(AUTHORIZATION_TOKEN);
		cb.setOAuthAccessTokenSecret(AUTHORIZATION_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Query query = new Query(keyword);
		QueryResult result;
		main_loop: do {
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) {
				User user = tweet.getUser();
				String loc = user.getLocation();
				String temp[] = loc.split(",");
				System.out.println("Size : " + temp.length);
				for (int i = 0; i < temp.length; i++) {
					String text = temp[i].trim();
					System.out.println("Text: " + text);
					boolean j = ReadStates.isUSState(text);
					System.out.println(j);
					if (ReadStates.isUSState(text)) {
						System.out.println("In if");
						if (temp[i].trim().length() == 2) {
							try {
								WriteFile.write(ReadStates.stateAndCode
										.get(temp[i].trim()));
								iteration++;
								System.out.println("Iteration : " + iteration);
								break;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println(e);
							}
						} else {
							try {
								WriteFile.write(temp[i].trim());
								iteration++;
								System.out.println("Iteration : " + iteration);
								break;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println(e);
							}
						}
					}
				}
				System.out.println(tweet.getText());
				if (iteration == numberOfTweets) {
					break main_loop;
				}
			}
		} while ((query = result.nextQuery()) != null);
		System.out.println("********* Location data fetched *************");
		if (LaunchWordCount.startHadoop()) {
			if (LaunchWordCount.moveWordCountInputFile()) {
				if (LaunchWordCount.submitWordCount()) {
					if (LaunchWordCount.moveHadoopOutput()) {
						if (LaunchWordCount.stopHadoop()) {
							ReadMapRedOutput.generateJSValues();
						}
					}
				}
			}
		}
		return "MININGSUCCESS";
	}

	public int getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(int numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}