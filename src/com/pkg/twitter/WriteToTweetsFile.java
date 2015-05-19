package com.pkg.twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToTweetsFile {

	public static void write(String args) throws IOException {
		System.out.println("Inside write file");

		File file = new File(ReadStates.path + "/tweets/tweets.txt");

		// During first iteration, clear the exisiting file
		if (StreamingSentimentAnalysis.iteration == 1) {
			System.out.println("Cleaning the existing tweets file");
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		}
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		// If there is a space in the state name, word count will consider
		// partial state name as one word, hence we will replace the state name
		// with underscore.
		bw.write(args + "\n");
		bw.close();
	}
}