package com.pkg.twitter;

/* This class stores the twitter authorization information in private variables. 
 * These credentials are used across the application whenever there is a need 
 * to collect tweet information */
public class Authorization {

	private final static String CONSUMER_KEY = "CYK0a04c5lzVSGa74JdQH0lpJ";
	private final static String CONSUMER_KEY_SECRET = "PJFC3DzXr7rhR90hgeR3rDX7wPwWAyWWNW70GrPXm0XzCJyqPL";
	private final static String AUTHORIZATION_TOKEN = "3025651503-SO8yqeqG3a8TCgl7doSu3BdoxZjVT4oePRzdpVU";
	private final static String AUTHORIZATION_SECRET = "ZJL1c1iRElBDMnou0Ltw7N9ti3ye7soXW0H2rfabO2n5R";

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