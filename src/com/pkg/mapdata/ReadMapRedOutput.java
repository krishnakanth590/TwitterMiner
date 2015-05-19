package com.pkg.mapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.pkg.twitter.*;

public class ReadMapRedOutput {

	public static ArrayList<Integer> counts = new ArrayList<Integer>();
	public static HashMap<String, Integer> stateCount = new HashMap<String, Integer>();
	public static HashMap<String, Double> statePercent = new HashMap<String, Double>();
	public static int totalTweets;

	// Read the wordcount output and store the information in static variables
	// that can be used across the classes
	public static void readWordCount() throws NumberFormatException,
			IOException {
		ReadStates rs = new ReadStates();
		BufferedReader br = new BufferedReader(new FileReader(ReadStates.path
				+ "/wordcountoutput/part-r-00000"));
		stateCount.clear();
		statePercent.clear();
		String readLine;
		while ((readLine = br.readLine()) != null) {
			String temp[] = readLine.split("\\t");
			counts.add(Integer.parseInt(temp[1].trim()));
			stateCount.put(temp[0].trim(), Integer.parseInt(temp[1].trim()));

		}
		br.close();

		System.out.println("End readWordCount");
	}

	// Generate percentage of tweets for each state
	public static void generatePercentages() {
		totalTweets = 0;
		// Count total number of tweets from all states
		for (int i = 0; i < counts.size(); i++) {
			totalTweets = totalTweets + counts.get(i);
		}

		// Calculate percentage of total tweets for each state
		for (int j = 0; j < ReadStates.stateNames.size(); j++) {

			if (!(stateCount.get(ReadStates.stateNames.get(j).trim()
					.replaceAll(" ", "_")) == null)) {
				double localCount = stateCount.get(ReadStates.stateNames.get(j)
						.trim().replaceAll(" ", "_"));

				double percentage = (localCount / totalTweets) * 100;
				statePercent.put(ReadStates.stateNames.get(j).trim()
						.replaceAll(" ", "_"), round(percentage, 2));
			} else {
				statePercent.put(ReadStates.stateNames.get(j).trim()
						.replaceAll(" ", "_"), 0.0);
			}

		}
		System.out.println("End generatePercentages");
	}

	// Method to generate JavaScript that contains variables storing the
	// percentage of tweets from each state. These variables will be used by
	// fips.js JavaScript to display the tweet information on Map`
	public static void writeToJS() throws IOException {

		File file = new File(ReadStates.path + "/js/values.js");

		// Clear content of file before writing new output
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();

		StringBuffer sbWriteToJS = new StringBuffer();
		FileWriter fw = new FileWriter(file, true);
		System.out
				.println("Read States size : " + ReadStates.stateNames.size());
		for (int i = 0; i < ReadStates.stateNames.size(); i++) {
			System.out.println("In for loop");
			if (i == 0) {
				sbWriteToJS.append("var percentageOfTweets = {");
				sbWriteToJS.append("\n");
			}
			sbWriteToJS.append(ReadStates.stateNames.get(i).trim()
					.replaceAll(" ", "_")
					+ " : "
					+ statePercent.get(ReadStates.stateNames.get(i).trim()
							.replaceAll(" ", "_")) + " , ");
			sbWriteToJS.append("\n");
			if (i == (ReadStates.stateNames.size() - 1)) {
				sbWriteToJS.append("};");
			}
		}
		fw.write(sbWriteToJS.toString());
		fw.close();

		System.out.println("End writeToJS");
	}

	// Utility method to round a double to two decimal places
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static void generateJSValues() throws NumberFormatException,
			IOException {
		readWordCount();
		generatePercentages();
		writeToJS();
		System.out.println("End");
	}
}