package com.pkg.sentiment;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SentimentAnalysisMapper extends
		Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	static int iteration = 0;
	String sentimentRating = null;

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		System.out.println("Mapper start");
		System.out.println("tweet :" + value.toString());
		SentimentClassifier sc = new SentimentClassifier();
		try {
			System.out.println("In try");
			iteration++;
			if (value.toString().length() > 0 && !(value == null)) {
				System.out.println("In if");
				sentimentRating = sc.classify(value.toString());
				System.out.println("Rating :" + sentimentRating);
				context.write(new Text(sentimentRating), one);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Mapper end");
	}
}
