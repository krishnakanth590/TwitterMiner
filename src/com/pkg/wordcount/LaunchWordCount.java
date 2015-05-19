package com.pkg.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.pkg.twitter.ReadStates;

@ManagedBean(name = "launchWordCount")
@SessionScoped
public class LaunchWordCount {
	// Start Hadoop
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

	// Stop hadoop
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

	// Submit the job by running the Jar command
	public static boolean submitWordCount() throws IOException,
			InterruptedException {
		System.out.println("Submit WordCount");
		Process p;

		// Delete output folder if already exists
		String deleteCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -rmr wordcountoutput";

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
				+ "jars/WordCount.jar com.pkg.wordcount.WordCountDriver wordcountinput wordcountoutput";

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

	// Move the file generate by java application into HDFS
	public static boolean moveWordCountInputFile() throws IOException,
			InterruptedException {
		System.out.println("Move input file to HDFS for WordCount");
		Process p;

		// Delete the wordcountinput folder
		String deleteCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -rmr wordcountinput";

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

		// Create wordcountinput folder
		String createFolderCommand = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -mkdir wordcountinput";

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
				+ ReadStates.path + "tweetoutput/data.txt wordcountinput";

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
	public static boolean moveHadoopOutput() throws IOException,
			InterruptedException {
		System.out.println("MoveHadoopOutput Start");

		// Delete if old files in wordcountoutput folder, if already exists
		try {

			String tempFile = ReadStates.path + "/wordcountoutput/part-r-00000";
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
		String command = "/home/krishnakanth/BigData/hadoop-1.0.3/bin/hadoop fs -get wordcountoutput/part-r-00000 "
				+ ReadStates.path + "/wordcountoutput/";

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

		System.out.println("Moved the wordcount output to project folder");
		return true;
	}
}