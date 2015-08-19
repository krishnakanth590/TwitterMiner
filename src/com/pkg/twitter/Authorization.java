package com.pkg.twitter;

/* This class stores the twitter authorization information in private variables. 
 * These credentials are used across the application whenever there is a need 
 * to collect tweet information */
public class Authorization {

	private final static String CONSUMER_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private final static String CONSUMER_KEY_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private final static String AUTHORIZATION_TOKEN = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private final static String AUTHORIZATION_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	public static String getConsumerKey() {
		System.out.println("Sharing CONSUMER_KEY");
		return CONSUMER_KEY;
	}

	public static String getConsumerKeySecret() {
		System.out.println("Sharing CONSUMER_KEY_SECRET");
		return CONSUMER_KEY_SECRET;
	}

	public static String getAuthorizationToken() {
		System.out.println("Sharing AUTHORIZATION_TOKEN");
		return AUTHORIZATION_TOKEN;
	}

	public static String getAuthorizationSecret() {
		System.out.println("Sharing AUTHORIZATION_SECRET");
		return AUTHORIZATION_SECRET;
	}
}