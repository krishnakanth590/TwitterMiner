package com.pkg.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.pkg.twitter.ReadStates;
import com.pkg.twitter.StreamingSentimentAnalysis;

@ManagedBean(name = "launchSentiment")
@SessionScoped
public class LaunchSentimentAnalysis {
	private double positivePercentage = 0;
	private double negativePercentage = 0;
	private double neutralPercentage = 0;
	private int positiveCount = 0;
	private int negativeCount = 0;
	private int neutralCount = 0;
	private double totalSentimentRatings = 0;
	private boolean showPieChart = false;

	public static boolean startHadoop() throws IOException,
			InterruptedException {
		System.out.println("Start hadoop");
		Process p;
		String command = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/start-all.sh";

		System.out.println("In try");
		p = Runtime.getRuntime().exec(command);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = "";
		System.out.println("Before while");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		// The node will be in safe mode for sometime after starting. Since, we
		// are executing other commands immediately after starting the node, we
		// have to manually turn off the safe mode
		System.out.println("Exiting safe mode");
		Process p1;
		String command1 = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop dfsadmin -safemode leave";

		System.out.println("In try");
		p1 = Runtime.getRuntime().exec(command1);
		p1.waitFor();
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(
				p1.getInputStream()));

		String line1 = "";
		System.out.println("Before while");
		while ((line1 = reader1.readLine()) != null) {
			System.out.println(line1);
		}
		return true;
	}

	public static boolean stopHadoop() throws IOException, InterruptedException {
		System.out.println("Stop hadoop");
		Process p;

		String command = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/stop-all.sh";

		System.out.println("In try");
		p = Runtime.getRuntime().exec(command);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = "";
		System.out.println("Before while");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		return true;
	}

	public static boolean submitSentiment() throws IOException,
			InterruptedException {
		System.out.println("Submit Sentiment");
		Process p;

		// Delete output folder if already exists
		String deleteCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -rmr sentimentoutput";

		System.out.println("Command : " + deleteCommand);

		System.out.println("In try");
		p = Runtime.getRuntime().exec(deleteCommand);

		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = "";
		System.out.println("Before while");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		// Submit the job

		String submitCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop jar "
				+ ReadStates.path
				+ "jars/SentimentAnalysis.jar com.pkg.sentiment.SentimentAnalysisDriver sentimentinput sentimentoutput";

		System.out.println("Command : " + submitCommand);

		System.out.println("In try");
		p = Runtime.getRuntime().exec(submitCommand);

		p.waitFor();
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line2 = "";
		System.out.println("Before while");
		while ((line2 = reader2.readLine()) != null) {
			System.out.println(line2);
		}

		System.out.println("Job Submitted");
		return true;
	}

	public static boolean moveSentimentInputFile() throws IOException,
			InterruptedException {
		System.out.println("Move input file to HDFS for Sentiment Analysis");
		Process p;

		// Delete the sentimentinput folder
		String deleteCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -rmr sentimentinput";

		System.out.println("Delete Command : " + deleteCommand);
		p = Runtime.getRuntime().exec(deleteCommand);

		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = "";
		System.out.println("Before while");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		// Create sentimentinput folder
		String createFolderCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -mkdir sentimentinput";

		System.out.println("Create folder command :" + createFolderCommand);
		p = Runtime.getRuntime().exec(createFolderCommand);

		p.waitFor();
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line2 = "";
		System.out.println("Before while");
		while ((line2 = reader2.readLine()) != null) {
			System.out.println(line2);
		}

		// Move the file to wordcountinput folder
		String moveFile = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -put "
				+ ReadStates.path + "tweets/tweets.txt sentimentinput";

		System.out.println("Command : " + moveFile);
		p = Runtime.getRuntime().exec(moveFile);

		p.waitFor();
		BufferedReader reader3 = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line3 = "";
		System.out.println("Before while");
		while ((line3 = reader3.readLine()) != null) {
			System.out.println(line3);
		}

		System.out.println("File copied to HDFS");
		return true;
	}

	// Move hadoop output file from HDFC to the project folder
	public static boolean moveSentimentHadoopOutput() throws IOException,
			InterruptedException {
		System.out.println("MoveSentimentHadoopOutput Start");

		// Delete if old files in wordcountoutput folder, if already exists
		try {

			String tempFile = ReadStates.path + "/sentimentoutput/part-r-00000";
			// Delete if tempFile exists
			File fileTemp = new File(tempFile);
			if (fileTemp.exists()) {
				fileTemp.delete();
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
			System.out.println(e);
		}

		Process p;
		String command = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -get sentimentoutput/part-r-00000 "
				+ ReadStates.path + "/sentimentoutput/";

		System.out.println("In try");
		p = Runtime.getRuntime().exec(command);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = "";
		System.out.println("Before while");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		System.out
				.println("Moved the sentiment analysis output to project folder");
		return true;
	}

	public String findSentiment() throws Exception {
		// ReadStates - eventhough this is not useful, it saves the path. Hence
		// we use it
		ReadStates rs = new ReadStates();
		// Fetch Tweets and save to tweets.txt file
		StreamingSentimentAnalysis.getTweets();

		// Move files and launch sentiment analysis map reduce job
		if (LaunchSentimentAnalysis.startHadoop()) {
			if (LaunchSentimentAnalysis.moveSentimentInputFile()) {
				if (LaunchSentimentAnalysis.submitSentiment()) {
					if (LaunchSentimentAnalysis.moveSentimentHadoopOutput()) {
						LaunchSentimentAnalysis.stopHadoop();
					}
				}
			}
		}

		BufferedReader br = new BufferedReader(new FileReader(ReadStates.path
				+ "sentimentoutput/part-r-00000"));
		String readLine;
		while ((readLine = br.readLine()) != null) {
			String[] temp = readLine.split("\\t");
			if (temp[0].trim().equalsIgnoreCase("pos")) {
				positiveCount = Integer.parseInt(temp[1]);
			} else if (temp[0].trim().equalsIgnoreCase("neg")) {
				negativeCount = Integer.parseInt(temp[1]);
			} else if (temp[0].trim().equalsIgnoreCase("neu")) {
				neutralCount = Integer.parseInt(temp[1]);
			}
		}
		br.close();
		totalSentimentRatings = positiveCount + negativeCount + neutralCount;
		positivePercentage = (positiveCount / totalSentimentRatings) * 100;
		negativePercentage = (negativeCount / totalSentimentRatings) * 100;
		neutralPercentage = (neutralCount / totalSentimentRatings) * 100;
		System.out.println("Positive percentage :" + positivePercentage);
		System.out.println("Negative percentage :" + negativePercentage);
		System.out.println("Neutral percentage :" + neutralPercentage);

		// Generate pie chart
		GeneratePieChart.generateChart();
		showPieChart = true;
		return "DONE";
	}

	public double getPositivePercentage() {
		return positivePercentage;
	}

	public void setPositivePercentage(double positivePercentage) {
		this.positivePercentage = positivePercentage;
	}

	public double getNegativePercentage() {
		return negativePercentage;
	}

	public void setNegativePercentage(double negativePercentage) {
		this.negativePercentage = negativePercentage;
	}

	public double getNeutralPercentage() {
		return neutralPercentage;
	}

	public void setNeutralPercentage(double neutralPercentage) {
		this.neutralPercentage = neutralPercentage;
	}

	public int getPositiveCount() {
		return positiveCount;
	}

	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}

	public int getNegativeCount() {
		return negativeCount;
	}

	public void setNegativeCount(int negativeCount) {
		this.negativeCount = negativeCount;
	}

	public int getNeutralCount() {
		return neutralCount;
	}

	public void setNeutralCount(int neutralCount) {
		this.neutralCount = neutralCount;
	}

	public double getTotalSentimentRatings() {
		return totalSentimentRatings;
	}

	public void setTotalSentimentRatings(double totalSentimentRatings) {
		this.totalSentimentRatings = totalSentimentRatings;
	}

	public boolean isShowPieChart() {
		return showPieChart;
	}

	public void setShowPieChart(boolean showPieChart) {
		this.showPieChart = showPieChart;
	}

}