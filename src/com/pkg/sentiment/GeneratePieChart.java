package com.pkg.sentiment;

import java.awt.Color;
import java.io.File;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.pkg.twitter.ReadStates;

public class GeneratePieChart {
	public static void generateChart() throws Exception {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);
		LaunchSentimentAnalysis lsa = (LaunchSentimentAnalysis) session
				.getAttribute("launchSentiment");
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Positive", lsa.getPositivePercentage());
		dataset.setValue("Negative", lsa.getNegativePercentage());
		dataset.setValue("Neutral", lsa.getNeutralPercentage());
		JFreeChart chart = ChartFactory.createPieChart("Sentiment Analysis",
				dataset, true, // include legend
				true, false);
		// Set colors to sections of plot
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("Positive", Color.GREEN);
		plot.setSectionPaint("Negative", Color.RED);
		plot.setSectionPaint("Neutral", Color.YELLOW);
		plot.setSimpleLabels(true);
		// Display percentages in the pie chart
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{1}%");

		plot.setLabelGenerator(gen);
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File pieChart = new File(ReadStates.path + "images/PieChart.jpeg");
		ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
	}

	public static void main(String args[]) throws Exception {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Positive", 40.5);
		dataset.setValue("Negative", 29.5);
		dataset.setValue("Neutral", 20);
		JFreeChart chart = ChartFactory.createPieChart("Sentiment Analysis",
				dataset, true, // include legend
				true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("Positive", Color.GREEN);
		plot.setSectionPaint("Negative", Color.RED);
		plot.setSectionPaint("Neutral", Color.YELLOW);
		plot.setSimpleLabels(true);
		// Display percentages in the pie chart
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{1}%");

		plot.setLabelGenerator(gen);
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File pieChart = new File("WebContent/images/PieChart.jpeg");
		ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);

	}
}