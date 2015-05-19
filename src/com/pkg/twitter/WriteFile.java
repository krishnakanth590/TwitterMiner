package com.pkg.twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {

	public static void write(String args) throws IOException {
		System.out.println("Inside write file");

		System.out.println("Project root: " + ReadStates.path);
		File file = new File(ReadStates.path + "/tweetoutput/data.txt");

		// During first iteration, clear the exisiting file
		if (StreamingHeatMap.iteration == 0) {
			System.out.println("Cleaning the existing file");
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		}
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		// If there is a space in the state name, word count will consider
		// partial state name as one word, hence we will replace the state name
		// with underscore.
		bw.write(args.replaceAll(" ", "_") + "\n");
		bw.close();
	}
}