package com.pkg.sentiment;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SentimentAnalysisDriver extends Configured implements Tool {

	public int run(String args[]) throws Exception {
		
		// Setting up summary job to print the node summary in Node_Summary
		// folder
		Configuration confSummary = this.getConf();
		Job summaryJob = new Job(confSummary, "SentimentAnalysisJob");
		summaryJob.setJarByClass(SentimentAnalysisDriver.class);
		summaryJob.setMapperClass(SentimentAnalysisMapper.class);
		summaryJob.setReducerClass(SentimentAnalysisReducer.class);
		FileInputFormat.addInputPath(summaryJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(summaryJob, new Path(args[1]));
		summaryJob.setMapOutputKeyClass(Text.class);
		summaryJob.setMapOutputValueClass(IntWritable.class);
		summaryJob.setOutputKeyClass(Text.class);
		summaryJob.setOutputValueClass(IntWritable.class);
		summaryJob.waitForCompletion(true);

		return 1;
	}

	public static void main(String args[]) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new SentimentAnalysisDriver(), args);
		System.exit(res);
	}
}
